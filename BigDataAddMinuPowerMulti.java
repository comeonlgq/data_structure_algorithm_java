package com.structure.interestmath;

import java.util.Scanner;

/**
 * 大数据的加减乘除方法
 * 
 * @author Administrator
 *
 */
public class BigDataAddMinuPowerMulti {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			String string = sc.next();
			String string2 = sc.next();

			add(string, string2);// 加法
			minus(string, string2);// 减法

			power(string, string2);// 幂指数

			multiplyAll(string, string2);// 乘法
		}
	}

	public static void add(String s1, String s2) {// 加法
		Pair rPair =new Pair(storeData(s1, s2, '+'));
		
		int []res=new int[rPair.data[0].length];
		for(int i=0;i<rPair.data[0].length;i++) {
			res[i]=rPair.data[0][i]+rPair.data[1][i];
		}
		int dot=rPair.dot;
		rPair=null;
		
		//处理进位问题
		for(int i=res.length-1;i>0;i--) {
			if(res[i]>10) {
				res[i-1]+=res[i]/10;
				res[i]=res[i]%10;
			}
		}

		String string=new String();
		for(int i=0;i<res.length;i++) {
			if(i==dot) {
				string=string+'.';
			}else {
				string+=res[i];
			}
		}
		System.out.println(s1+" 与 "+s2+"的和为：");
		System.out.println(string);
		
	}

	public static void minus(String s1, String s2) {// 减法
		Pair rPair =new Pair(storeData(s1, s2, '-'));
		
		int []res=new int[rPair.data[0].length];
		for(int i=res.length-1;i>0;i--) {
			if(rPair.data[0][i]-rPair.data[1][i]<0) {//不够减，向高位借一位
				rPair.data[0][i]=rPair.data[0][i]+10;
				rPair.data[0][i-1]--;
			}
			res[i]=rPair.data[0][i]-rPair.data[1][i];
		}
		res[0]=rPair.data[0][0]-rPair.data[1][0];
		int dot=rPair.dot;
		boolean flag=rPair.sign;
		rPair=null;
		
		String string=new String();
		String result=new String();
		if(flag) {
			result+='-';
		}
		for(int i=0;i<res.length;i++) {
			if(i==dot) {
				string=string+'.';
			}else {
				string+=res[i];
			}
		}
		int i=0;
		while (string.charAt(i)==0) {//去除前面的0
			i++;
		}
		for(i=0;i<string.length();) {
			if(string.charAt(i)=='0') {
				i++;
			}else {
				break;
			}
		}
		result+=string.substring(i,string.length());
			
		System.out.println(s1+" 与 "+s2+"的差为：");
		System.out.println(result);
		
	}

	
	// 加减法综合
	public static Pair storeData(String s1, String s2, char operator) {// 该函数用于将数据放入矩阵中
		Pair rPair;
		// 首先判断小数的位置
		int dot1 = -1;
		int dot2 = -1;
		for (int i = 0; i < s1.length(); i++) {
			if (s1.charAt(i) == '.') {
				dot1 = i;
				break;
			}
		}
		for (int i = 0; i < s2.length(); i++) {
			if (s2.charAt(i) == '.') {
				dot2 = i;
				break;
			}
		}
		// 最长的小数位
		int n = 0;
		int dotloc = -1;
		boolean sign = false;
		int decimalBit1 = s1.length() - 1 - dot1;
		int decimalBit2 = s2.length() - 1 - dot2;
		if (dot1 != -1 && dot2 != -1) {// 两个都有小数

			n = Math.max(decimalBit1, decimalBit2);// 表示的是存储的行数
			n += Math.max(dot1, dot2);
			dotloc = Math.max(dot1, dot2);

			// 保证s1中存放的是较大的值，s2中存放的是较小的值
			if (dot1 < dot2) {// 表示s1比s2小
				String temp = s1;
				s1 = s2;
				s2 = temp;
				if (operator == '-') {
					sign = true;
				}
			}
			if (dot1 == dot2) {// s1和s2的整数部分的长度相同
				int[] dis = new int[dot1 + 1];
				boolean flag = false;// 用于表示是否s1大于s2,false:是的，true:否
				int sum = 0;
				for (int i = 0; i <= dot1; i++) {
					dis[i] = s1.charAt(i) - s2.charAt(i);
					sum += dis[i];
					if (dis[i] < 0) {
						flag = true;
						break;
					}
					if(dis[i]>0) break;
				}
				if (flag) {// s2的整数部分大于是s1的整数部分
					String temp = s1;
					s1 = s2;
					s2 = temp;
					if (operator == '-') {
						sign = true;
					}
				}
				dis = null;
				if (sum == 0 && !flag) {// 整数部分相等，比较小数部分
					dis = new int[Math.max(decimalBit1, decimalBit2)];
					for (int i = 0; i < dis.length; i++) {
						
						//s1和s2的小数部分均没有遍历完
						if (dot1 + 1 + i < s1.length() && dot2 + 1 + i < s2.length()) {
							dis[i] = s1.charAt(dot1 + 1 + i) - s2.charAt(dot2 + 1 + i);
						}
						//s2的小数已经遍历完，是s1还没有
						if (dot1 + 1 + i < s1.length() && dot2 + 1 + i > s2.length()) {
                             dis[i]=s1.charAt(dot1 + 1 + i);
						}
						//s2的小数已经遍历完，是s1还没有
						if (dot1 + 1 + i < s1.length() && dot2 + 1 + i > s2.length()) {
							 dis[i]=-s2.charAt(dot2 + 1 + i);
						}
						if(dis[i]<0) {
							flag=true;
							break;
						}
						if(dis[i]>0) break;
					}
	
					if(flag) {
						String temp = s1;
						s1 = s2;
						s2 = temp;
						if (operator == '-') {
							sign = true;
						}
					}
				}

			}

		}
		if (dot1 * dot2 < 0) {// 有一个为小数
			if (dot1 > dot2) {// s1是小数
				n = Math.max(s2.length(), dot1);
				dotloc = n;
				n += decimalBit1;
				
				if(s2.length()>dot1) {//整数部分s2长度比s1大
					String temp = s1;
					s1 = s2;
					s2 = temp;
					if (operator == '-') {
						sign = true;
					}
				}
				
				if(s2.length()==dot1) {//整数部分长度相同
					boolean flag=false;
					for(int i=0;i<s2.length();i++) {
						if(s1.charAt(i)-s2.charAt(i)<0) {
							flag=true;break;
						}
						if(s1.charAt(i)-s2.charAt(i)>0) {
							break;
						}
					}
					if(flag) {
						String temp = s1;
						s1 = s2;
						s2 = temp;
						if (operator == '-') {
							sign = true;
						}
					}
				}
				
				
			} else {// s2是小数
				n = Math.max(s1.length(), dot2);
				dotloc = n;
				n += decimalBit2;
				
				int sum=0;boolean flag=false;
				int i=0;
				for(;i<s1.length();i++) {
					if(s1.charAt(i)-s2.charAt(i)<0) {
						flag=true;break;
					}
					if(s1.charAt(i)-s2.charAt(i)>0) {
						break;
					}
					sum+=s1.charAt(i)-s2.charAt(i);
				}
				if(flag || (!flag && sum==0 && i==s1.length())) {//整数部分小，或者是整数部分相等
					String temp = s1;
					s1 = s2;
					s2 = temp;
					if (operator == '-') {
						sign = true;
					}
				}
			}
		}
		if (dot1 == -1 && dot2 == -1) {// 两个都是整数
			n = Math.max(s1.length(), s2.length());
			dotloc=n;
			if(s1.length()<s2.length()) {//s2比s1大
				String temp=s1;
				s1=s2;
				s2=temp;
				if (operator == '-') {
					sign = true;
				}
			}
			
			if(s1.length()==s2.length()) {//s1与s2的长度相等
				boolean flag=false;
				for(int i=0;i<n;i++) {
					if(s1.charAt(i)-s2.charAt(i)<0) {
						flag=true;
						break;
					}
					if(s1.charAt(i)-s2.charAt(i)>0) {
						break;
					}
				}
				if(flag) {
					String temp=s1;
					s1=s2;
					s2=temp;
					if (operator == '-') {
						sign = true;
					}
				}
			}	
		}
		int[][] data = new int[2][n];
        for(int i=0,k=0;i<n;i++) {//放入s1
        	if(s1.charAt(k)!='.') {
        	   int tt=Integer.parseInt(String.valueOf(s1.charAt(k++)));//一定需要转换，否则是按照ascii码对应的值进行计算的
        	   data[0][i]=tt;
        	}
        	else {
        		k++;
        	}
        }
        int i=0,k=0;
        if(s2.contains(".")) {
        	i=dotloc-Math.min(dot1, dot2)-1;
        }else {
        	i=dotloc-s2.length();
        }
 
        for(;i<n && k<s2.length();i++) {//放入s2
        	if(s2.charAt(k)=='.') {
        		k++;
        	}else {
        		int t2 = Integer.parseInt(String.valueOf(s2.charAt(k++)));
        		data[1][i]=t2;
        	}
        }
		rPair=new Pair(data, dotloc, sign);

		return rPair;
	}

	
	public static void power(String s1, String s2) {// 幂乘

		String tString = new String();
		tString = s1;
		int n = 0;
		if (s2.contains(".")) {// 如果幂数的次数为小数，向下取整
			int tt = -1;
			for (int i = 0; i < s2.length(); i++) {
				if (s2.charAt(i) == '.') {
					tt = i;
				}
			}
			s2 = s2.substring(0, tt);
		}
		n = Integer.parseInt(s2);

		for (int i = 1; i < n / 2; i++) {// 计算一半的幂次的值
			tString = multiplyAll(tString, s1);
		}
		if (n > 1) {// 半 * 半 = 全
			tString = multiplyAll(tString, tString);
		}

		if (n % 2 == 1 && n > 1)
			tString = multiplyAll(tString, s1);
		System.out.println(tString);
	}

	public static String multiplyAll(String s1, String s2) {// 该算法适用于所有的类型，整数*小数 或者小数*小数，或者整数*整数
		int len1 = s1.length();
		int len2 = s2.length();
		int dot1 = -1;// 用于记录小数点的位置
		int dot2 = -1;

		if (s1.contains(".")) {
			len1--;
		}
		if (s2.contains(".")) {
			len2--;
		}

		int[][] a = new int[len1][len2];
		for (int i = 0; i < len1; i++)// 初始化
			for (int j = 0; j < len2; j++)
				a[i][j] = 0;
		for (int i = 0, k1 = 0; i < len1; i++, k1++) {// 按位相乘
			if (s1.charAt(k1) == '.') {
				dot1 = k1;
				k1++;
			}
			int t1 = Integer.parseInt(String.valueOf(s1.charAt(k1)));
			for (int j = 0, k2 = 0; j < len2; j++, k2++) {
				if (s2.charAt(k2) == '.') {
					dot2 = k2;
					k2++;
				}
				int t2 = Integer.parseInt(String.valueOf(s2.charAt(k2)));
				a[i][j] = t1 * t2;
				// System.out.print(a[i][j]+" ");
			}
			// System.out.println();
		}
		// System.out.println();

		// 首先是对角线位置相加
		int result[] = new int[len1 + len2 - 1];
		result[0] = a[0][0];
		result[len1 + len2 - 2] = a[len1 - 1][len2 - 1];
		int row = 0, col = 1;
		while (row + col <= len1 + len2 - 3) {// 对角线元素相加

			for (int i = 0; i <= Math.min(col + row, len1 - 1); i++)
				for (int j = 0; j <= Math.min(col + row, len2 - 1); j++) {
					if (i + j == row + col) {
						result[row + col] += a[i][j];
					}
				}
			if (col < len1)
				col++;
			else {
				row++;
				col = len1 - 1;
			}

		}
		// System.out.println(Arrays.toString(result));
		int c = 0;
		// 处理进位问题
		for (int i = result.length - 1; i > 0; i--) {
			c = result[i] / 10;
			result[i - 1] += c;
			result[i] = result[i] % 10;
		}
		// System.out.println(Arrays.toString(result));

		// 计算小数点的个数
		int count = 0;
		if (dot1 == -1 && dot2 == -1) {
			count = 0;
		}
		if (dot1 != -1 && dot2 == -1) {
			for (int i = 0; i < s1.length() - 1 - dot1; i++)
				count++;
		}
		if (dot1 == -1 && dot2 != -1) {
			for (int i = 0; i < s2.length() - 1 - dot2; i++)
				count++;
		}

		if (dot1 != -1 && dot2 != -1) {
			for (int i = 0; i < s1.length() - 2 - dot1 + s2.length() - dot2; i++)
				count++;
		}

		String res = new String();
		for (int i = 0; i < result.length; i++) {
			if (i == result.length - count) {
				res = res + '.';
			}
			res = res + result[i];
		}
		// System.out.println(res);
		return res;
	}
	
	public static void divide(String s1,String s2) {//除法
		
	}
	
	

	static class Pair {
		int[][] data;// 存放的数据
		int dot;// 点的位置
		boolean sign;// 是否需要加符号（考虑到减法）

		public Pair(int [][]data,int dot,boolean sign) {
			this.data=new int[2][data[0].length];
			for(int i=0;i<2;i++)
				for(int j=0;j<data[0].length;j++)
					this.data[i][j]=data[i][j];
			this.dot=dot;
			this.sign=sign;
			
		}
		
		public Pair(Pair r) {
			this.data=new int[2][r.data[0].length];
			this.data=r.data;
			this.dot=r.dot;
			this.sign=r.sign;
		}
	}

}
