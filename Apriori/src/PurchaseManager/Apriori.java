package PurchaseManager;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class Apriori {
    private static int  MIN_SUPPORT_COUNT = 3;
    private static final double MIN_CONFIDENCE = 0.4;
    private static int threshold;
    private ArrayList<Set<String>> transactionRecord;
    private List<Map<Set<String>,Integer>>result=new ArrayList<>();
    public Apriori(){
        this.transactionRecord = readFile("Book1.csv");
        threshold=MIN_SUPPORT_COUNT;
    }
    public void updateApriori() throws IOException {
        result.add(new HashMap<>());
        Map<Set<String>,Integer>counter=new HashMap<>();
        for(Set<String>transaction:transactionRecord){
            for(String item:transaction){
                Set<String>it=new HashSet<>();
                it.add(item);
                if(counter.containsKey(it)){
                    counter.put(it,counter.get(it)+1);
                }
                else{
                    counter.put(it,1);
                }
            }
        }
        Map<Set<String>,Integer>L0=new HashMap<>();
        for(Map.Entry<Set<String>,Integer>entry:counter.entrySet()){
            if(entry.getValue()>=threshold){
                L0.put(entry.getKey(),entry.getValue());
            }
        }
        result.add(L0);

        int k=2;
        while(true){
            Map<Set<String>,Integer>LK=new HashMap<>();
            Map<Set<String>,Integer>LK_1=result.get(k-1);
            List<Set<String>> keys = new ArrayList<>(LK_1.keySet());

            for(int i=0;i<keys.size()-1;i++){
                for(int j=i+1;j<keys.size();j++){
                    Set<String>joined=new HashSet<>();
                    joined.addAll(keys.get(i));
                    joined.addAll(keys.get(j));
                    if(joined.size()==k){
                        int supportCount=0;
                        for(Set<String>transaction:transactionRecord){
                            if(transaction.containsAll(joined)){
                                supportCount++;
                            }
                        }
                        if(supportCount>=threshold){
                            LK.put(joined,supportCount);
                        }
                    }
                }
            }
            if(LK.isEmpty())break;
            result.add(LK);
            k++;
        }
        System.out.println(result);
        FileWriter fw = new FileWriter("recommendations.csv", false);
        PrintWriter pw = new PrintWriter(fw);
        for(Map<Set<String>,Integer>L:result){
            for(Map.Entry<Set<String>,Integer>itemSet:L.entrySet()){
                Iterator<String>iterator=itemSet.getKey().iterator();
                String items="";
                while(iterator.hasNext()){
                    items+=iterator.next();
                    if(iterator.hasNext()){
                        items+=",";
                    }
                }
                pw.write(items);
                pw.write("\n");
            }
        }
        pw.close();
        fw.close();

    }
    public ArrayList<Set<String>> readFile(String fileName){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ArrayList <Set<String>> transactionRecord = new ArrayList<>();
        String transactionGetter;
        while(true){
            try {
                if (!((transactionGetter = reader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] items = transactionGetter.split(",");
            Set<String> transaction = new HashSet<>();
            for (String item : items) {
                transaction.add(item);
            }

            transactionRecord.add(transaction);
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return transactionRecord;
    }


    public ArrayList<String> generateRecommendations(ArrayList<String> cart) {
//        System.out.println("cart: "+cart);
        ArrayList<Set<String>>calculatedResult=readFile("recommendations.csv");
        Set<String>orderedItem=new HashSet<>(cart);
        Set<String>recommendations=new HashSet<>();

        for(int i=0;i<calculatedResult.size();i++){
            if(calculatedResult.get(i).containsAll(orderedItem) && !orderedItem.isEmpty()){
                Set<String>numerator=new HashSet<>();
                calculatedResult.get(i).removeAll(orderedItem);
//                System.out.println("Calculated: "+calculatedResult.get(i));

                numerator.addAll(calculatedResult.get(i));
                numerator.addAll(orderedItem);

                double numeratorSupport=0;
                double denominatorSupport=0;

                for(Set<String>transaction:transactionRecord){
                    if(transaction.containsAll(numerator)){
                        numeratorSupport++;
                    }
                    if(transaction.containsAll(orderedItem)){
                        denominatorSupport++;
                    }
                }
                double confidence=numeratorSupport/denominatorSupport;
                if(confidence>=MIN_CONFIDENCE){
                    recommendations.addAll(calculatedResult.get(i));
                }
            }
        }
//        System.out.println(recommendations);
        return new ArrayList<>(recommendations);
    }

}
