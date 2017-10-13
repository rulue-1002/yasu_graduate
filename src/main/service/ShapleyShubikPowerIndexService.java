package main.service;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import main.util.StringUtil;

public class ShapleyShubikPowerIndexService {

	private Long n;
	private Long q;
	private Long[] w;
	private Long[] wd;
	private LinkedHashMap<Entry<Long,Long>, Long> cks;
	private LinkedHashMap<Entry<Long,Long>, Long> dks;

	public BigDecimal calcSSPI () {


		Scanner in = new Scanner(System.in);
		try {
			// input n
			System.out.print("n=");
			n = Long.valueOf(in.next());

			// input q
			System.out.print("q=");
			q = Long.valueOf(in.next());

			// input w[j]
			// 通常配列のインデックスは0始まりだが、Wolfram上では1始まりであるため、
			// 配列の個数としては、n+1作る必要がある。
			w = new Long[n.intValue()+1];
			for (int j=1; j<n+1; j++) {
				System.out.print("w[" + j + "]=");
				w[j] = Long.valueOf(in.next());
			}

			wd = new Long[n.intValue()+1];
			// L6:Do[...]{i,1,n}
			for (int i=1; i<=n; i++) {
				System.out.println("i=" + i);
				// L7,8:Do[If[j<i...]{j,1,n-1}
				for (int j=1; j <= n-1; j++) {
					if (j < i) {
						wd[j] = w[j];
					} else {
						wd[j] = w[j+1];
					}
					System.out.println("wd[" + j + "]=" + wd[j]);
				}

				// L8:Do[c[k,s]...]{k,0,q-1}{s,0,n-1}
				cks = new LinkedHashMap<Entry<Long,Long>, Long>();
				for (int k=0; k<=q-1; k++) {
					for (int s=0; s<=n-1; s++) {
						Map.Entry<Long,Long> ks = newEntry(Long.valueOf(k), Long.valueOf(s));
						if (k == 0 && s == 0) {// L9:c[0,0]=1;
							cks.put(ks,1L);
						} else {
							cks.put(ks,0L);
						}
					}
				}
				System.out.println("c[k,s]=" + cks);

				// L10:Do[d[k,s]=c[k,s]...,{s,0,n-1}]
				dks.putAll(cks);
				System.out.println("d[k,s]=" + dks);

			}

		} catch (Exception e) { // catch：tryブロック内で例外が発生した場合に行う処理を書く
			System.out.println("some error occured. Error message is " + e.getMessage());
			System.out.println(StringUtil.getStackTraceStr(e));
		} finally { // finally：何があろうと最後に実行したい処理を書く
			// ストリーム系は開けたら必ず閉じるのです。
			if (in != null) {
				in.close();
			}
		}

		return new BigDecimal(n + q);
	}


	private <K, V> Map.Entry<Long,Long> newEntry(Long k, Long s) {
		return new AbstractMap.SimpleEntry<>(k, s);
	}


}
