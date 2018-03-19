package com.nuist.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 竞赛算法，通用
 *    该方法采用逆向思维，改 + 和 / 为 - 和 *
 *    其实也应该算是穷举法
 * 项目名称：grad-project   
 * 类名称：Competition   
 * @version
 * 类描述：
 * @version   
 * 创建人：luocf   
 * @version
 * 创建时间：2015年5月19日 上午11:04:22 
 * @version  
 * 修改人：luocf     修改时间：2015年5月19日 上午11:04:22   
 * @version
 * 修改备注：   
 *
 */
public class Competition {
	
	private static final String PATTERN = "(\\d)(?=.*\1)";
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		//定义一个要求结果的数组
		int[] argus = {110,111,112};
		mainFunction(argus);
		long endTime = System.currentTimeMillis();
		System.out.print(endTime - startTime);
	}

	/**
	 * 主函数
	 * mainFunction  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年5月15日 下午5:07:37
	 */
	public static void mainFunction(int[] args) {
		int ALL_NUM_COUNT = 9;
		List<Integer> allDivisor = new ArrayList<Integer>();// 定义所有的除数
		int maxDivLen = 0;// 除数最长位数
		int minDivLen = 0;// 除数最短位数
		for (int argInd = 0; argInd < args.length; argInd++) {
			List<Integer> allAddend = getAllAddend(args[argInd]);// 先获取所有可能的加数
			for (int i = 0, n = allAddend.size(); i < n; i++) {
				int addend = allAddend.get(i);// 被加数
				int consult = args[argInd] - addend;// 商，也即加数
				int addendLen = (addend + "").length();// 加数的长度
				int consultLen = (consult + "").length();// 商的长度
				//接下来的两行代码应该是最关键的了
				// 求除数的最多位数 ((a-b).len + d.len - 1)[c.minLen] + b.len + d.len = 9
				// ((a-b).len + d.len)[c.maxLen] + b.len + d.len = 9
				int innerMaxDivLen = (int) Math.ceil((double) (ALL_NUM_COUNT + 1 - addendLen - consultLen) / 2);
				int innerMinDivLen = (int) Math.ceil((double) (ALL_NUM_COUNT - addendLen - consultLen) / 2);
				if (innerMaxDivLen != maxDivLen || innerMinDivLen != minDivLen) {//为了减少循环次数
					maxDivLen = innerMaxDivLen;
					minDivLen = innerMinDivLen;
					allDivisor = getAllDivisor((int) Math.pow(10, minDivLen - 1), (int) Math.pow(10, maxDivLen) - 1);
				}
				for (int j = 1, m = allDivisor.size(); j < m; j++) {
					int dividend = consult * allDivisor.get(j);
					int[] allNum = {addend, allDivisor.get(j), dividend};
					if (!checkNum(allNum)) {
						System.out.println(args[argInd] + "=" + addend + "+" + dividend + "/" + allDivisor.get(j));
					}
				}
			}
		}
	}

	/**
	 * 根据输入的值计算所有的可能的加数（b）
	 * getAllAddend  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年5月15日 下午5:08:21
	 */
	private static List<Integer> getAllAddend(int sum) {
		List<Integer> allAddend = new ArrayList<Integer>();
		// 首先，这个加数最大肯定比要求的数小2，如求105，则addend最大为103（暂未考虑含0）
		for (int i = 1; i < sum - 1; i++) {
			String diff = (sum - i) + "";// 结果减去加数，也即另两个数的商
			int diffLen = diff.length();
			int[] addendList = {i};
			// 其次，这个商最后一位肯定不会是1,
			if (!checkNum(addendList) && !"1".equals(diff.substring(diffLen - 1))) {
				allAddend.add(i);
			}
		}
		return allAddend;
	}

	/**
	 * 在主函数中计算出了除数的最大值和最小值，根据最大值和最小值来求出所有可能的除数（d）
	 * getAllDivisor  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年5月15日 下午5:08:06
	 */
	private static List<Integer> getAllDivisor(int minDiv, int maxDiv) {
		List<Integer> allDivisor = new ArrayList<Integer>();
		for (int i = minDiv; i < maxDiv; i++) {
			int[] divisorList = {i};
			if (!checkNum(divisorList)) {
				allDivisor.add(i);
			}
		}
		return allDivisor;
	}

	/**
	 * 检查是否符合1-9不重复 checkNum
	 * @param arr arr数组长度为1，检查一个数字是否有重复的数字，有没有0
	 * @param arr arr数组长度为3，检查三个数字是否满足1-9全用且不重复，不含0
	 * @return
	 * @throws
	 * @author luocf
	 * @date 2015年5月15日 下午1:07:47
	 */
	private static boolean checkNum(int[] arr) {
		StringBuffer numStrBuf = new StringBuffer();// 把数组拼成字符串
		int numLen = arr.length;// 数组长度
		for (int i = 0; i < numLen; i++) {
			numStrBuf.append(arr[i]);
		}
		String numStr = numStrBuf.toString();
		if (numStr.indexOf("0") != -1) {// 检查是否含0，有0直接退出
			return true;
		}
		char[] numArr = numStr.toCharArray();// 按字符拆分成数组
//		int numArrLen = numArr.length;// 拆分后数组长度
		int numArrLen = numStr.length();
		boolean isMatch = false;// 初始值
		if (numArrLen > 1) {// 如果是位数只有一位，就无须往下进行
//			if (numStr.matches(PATTERN) || (numLen == 3 && numArrLen != 9)) {
//				isMatch = true;
//			}
			Arrays.sort(numArr);// 对数组进行排序
			for (int i = 0; i < numArrLen - 1; i++) {// 检查是否有符合要求
				// 有重复数字或者当输入了三个数据无重复数字但是总长度不够9位数时直接退出
				if (numArr[i] == numArr[i + 1] || (numLen == 3 && numArrLen != 9)) {
					isMatch = true;
					break;
				}
			}
		}
		return isMatch;
	}
}
