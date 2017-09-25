package com.structure.interestmath;
/**
 * 大数据
 * 这是一段幂数与乘法的例子，乘法可以实现整数与整数相乘，整数与小数相乘，小数与小数相乘
 * 幂数函数可以实现：整数的整数次幂次，小数的整数次幂次，以及它们的含有小数的幂次，
 * 不过如果幂次为小数，文中直接向下取整，即当幂次为2.2,2.5,2.6等一些数时，幂次取为2.
 */
import java.util.Scanner;

public class PowerAndMultiply {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			String string = sc.next();
			String string2 = sc.next();

			power(string, string2);// 幂指数
            
			multiplyAll(string, string2);// 乘法
		}
	}

	public static void power(String s1, String s2) {// 幂乘

		String tString = new String();
		tString = s1;
		int n=0;
		if(s2.contains(".")) {//如果幂数的次数为小数，向下取整
			int tt=-1;
			for(int i=0;i<s2.length();i++) {
				if(s2.charAt(i)=='.') {
					tt=i;
				}
			}
			s2=s2.substring(0, tt);
		}
		n= Integer.parseInt(s2);
		

		for (int i = 1; i < n / 2; i++) {//计算一半的幂次的值
			tString = multiplyAll(tString, s1);
		}
		if (n > 1) {//半 * 半 = 全
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
   

}
