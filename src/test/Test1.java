public class Test1 {
    public static void main(String[] args) {
        String oldValue="0.1.1";
        String newValue ="0.1.2";
        String childValue ="0.1.1.1";
        if(!oldValue.equals(newValue)){
            if(childValue.indexOf(oldValue)==0){
                childValue = newValue+childValue.substring(oldValue.length());
                System.out.println("childValue = " + childValue);
            }
        }
    }
}
