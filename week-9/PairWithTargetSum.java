import java.util.HashMap;

public class PairWithTargetSum{
    static boolean hasTargetSum(int[] nums,int target){
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            int com = target - nums[i];
            if(map.containsKey(com)){
                return true;
            }
            map.put(nums[i],i);
        }
        return false;
    }
    public static void main(String[] args) {
        int[] nums = {2,7,11,15};
        int target = 9;
        System.out.println(hasTargetSum(nums, target));
        
    }
}