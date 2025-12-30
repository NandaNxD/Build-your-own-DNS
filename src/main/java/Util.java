import java.util.ArrayList;
import java.util.List;

public class Util {
    static byte[] convertByteArrayListToByteArray(ArrayList<Byte> list) {
        byte[] out = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            out[i] = list.get(i);
        }
        return out;
    }

    static byte[] mergeByteArrayListToSingleByteArray(List<byte[]> byteArraylist){
        int length=byteArraylist.stream().reduce(0,(sum,arr)-> sum + arr.length,Integer::sum);

        byte[] mergedArray=new byte[length];

        int index=0;
        for(byte[] array:byteArraylist){
            if(array!=null){
                System.arraycopy(array,0,mergedArray,index,array.length);
                index+=array.length;
            }
        }

        return mergedArray;
    }
}
