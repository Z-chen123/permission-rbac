import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Test2 {
    public static void main(String[] args) {

        //0 0.1,0.2
/*        String oldValue="0.1.1";
        String newValue ="0.1.2";
        String childValue ="0.1.1.1, 0.1.1.2.2";*/
        System.out.println("请求旧的层级关系:");
        Scanner sc = new Scanner(System.in);
        String oldValue = sc.next();
        System.out.println("oldValue = " + oldValue);
        System.out.println("请输入新的层级关系:");
        String newValue = sc.next();
        System.out.println("newValue = " + newValue);
        System.out.println("请输入旧层级的子层级");
        String oldChildValue = sc.next();
        System.out.println("oldChildValue = " + oldChildValue);
        List<String> list = Arrays.asList(oldChildValue.split(","));
        //定义新集合存值
        ArrayList<String> list1 = Lists.newArrayList();
        if(!oldValue.equals(newValue)){
        for(int i =0;i<list.size();i++){
            String str = list.get(i);
            if(str.indexOf(oldValue)==0){
                str = newValue+str.substring(oldValue.length());
                System.out.println("str = " + str);
                list1.add(str);
            }
        }
        }
        //集合转字符串
            StringBuilder builder = new StringBuilder();
            for(int i =0;i<list1.size();i++){
                String s = list1.get(i);
                builder.append(s);
                if(i!=list1.size()){
                builder.append(",");
                }
            }
            System.out.println("转换后的字符串:");
            System.out.println("新的子层级 = " + builder.toString());

    }
}
