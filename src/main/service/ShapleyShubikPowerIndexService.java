package main.service;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import main.util.NumberUtil;
import main.util.StringUtil;

public class ShapleyShubikPowerIndexService {

	private Long n;
	private Long q;
	private Long[] w;
	private Long[] wd;
	private LinkedHashMap<Entry<Long,Long>, Long> cks;
	private LinkedHashMap<Entry<Long,Long>, Long> dks;
	private Long[] ss;
	private Long[] bz;
	private Long[] shapleyShubikArray;
	private Long[] banzhafArray;


	public void calcSSPI () {


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
			ss = new Long[n.intValue()+1];
			bz = new Long[n.intValue()+1];
			shapleyShubikArray = new Long[n.intValue()+1];
			banzhafArray = new Long[n.intValue()+1];
			cks = new LinkedHashMap<Entry<Long,Long>, Long>();
			dks = new LinkedHashMap<Entry<Long,Long>, Long>();
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
				System.out.println("initial c[k,s]=" + cks);

				// L10:Do[d[k,s]=c[k,s]...,{s,0,n-1}]
				dks.putAll(cks);
				System.out.println("initial d[k,s]=" + dks);

				Entry<Long,Long> keyDks = null;
				Entry<Long,Long> keyCks = null;
				Long dksValue = null;
				// L11:Do[ → L16:];
				for (int j=1; j<=n-1; j++) {
					// L12,13:Do[d[k,s]=d[k,s]+…,{s,1,n-1}];
					for (int k=wd[j].intValue(); k<=q-1; k++) {
						for (int s=1; s<=n-1; s++) {
							keyDks = this.newEntry(Long.valueOf(k), Long.valueOf(s));
							keyCks = this.newEntry(Long.valueOf(k-wd[j]), Long.valueOf(s-1));
							dksValue = dks.get(keyDks) + cks.get(keyCks);
							dks.put(keyDks, dksValue);
						}
					}

					// L14,15:Do[c[k.s]…
					for (int k=0; k<=q-1; k++) {
						for (int s=0; s<=n-1; s++) {
							keyDks = this.newEntry(Long.valueOf(k), Long.valueOf(s));
							keyCks = this.newEntry(Long.valueOf(k), Long.valueOf(s));
							cks.put(keyCks,dks.get(keyDks));
						}
					}
				}
				System.out.println("calced c[k,s]=" + cks);
				System.out.println("calced d[k,s]=" + dks);

				// L17
				ss[i] = Long.valueOf(0);
				bz[i] = Long.valueOf(0);

				Long factS = null;
				Long factNS1 = null;
				Long factN = null;
				Long numerator = null;
				Long devided = null;
				// L18:Do[ss[i]=…
				for (int k=q.intValue()-w[i].intValue(); k<=q-1; k++) {
					for (int s=1; s<=n-1; s++) {
						// s!
						factS = NumberUtil.calcFactorial(Long.valueOf(s));
						// (n-s-1)!
						factNS1 = NumberUtil.calcFactorial(Long.valueOf(n-s-1));
						// n!
						factN = NumberUtil.calcFactorial(Long.valueOf(n));

						// s!(n-s-1)!c[k,s]
						numerator = factS * factNS1 * cks.get(this.newEntry(Long.valueOf(k), Long.valueOf(s)));
						// s!(n-s-1)!c[k,s]/n!
						devided = numerator / factN;

						ss[i] = ss[i] + devided;
						bz[i] = bz[i] + cks.get(this.newEntry(Long.valueOf(k), Long.valueOf(s)));
					}
				}



				bz[i] = bz[i] / Long.valueOf(new Double(Math.pow(2d,n.doubleValue()-1d)).longValue());
			}

			for (int i=1; i<=n; i++) {
				shapleyShubikArray[i] = ss[i];
				banzhafArray[i] = bz[i];
			}

			// wolfram と java との配列の初期インデックスの差（wolfram=1,java=0）により、
			// 配列ごと表示させると必ず「null,」が入ってしまうため、省いて表示させる。
			System.out.println("Shapley-Shubik=" + Arrays.toString(shapleyShubikArray).replaceFirst("null, ", ""));
			System.out.println("Banzhaf=" + Arrays.toString(banzhafArray).replaceFirst("null, ", ""));

		} catch (Exception e) { // catch：tryブロック内で例外が発生した場合に行う処理を書く
			System.out.println("some error occured. Error message is " + e.getMessage());
			System.out.println(StringUtil.getStackTraceStr(e));
		} finally { // finally：何があろうと最後に実行したい処理を書く
			// ストリーム系は開けたら必ず閉じるのです。
			if (in != null) {
				in.close();
			}
		}
	}


	private <K, V> Map.Entry<Long,Long> newEntry(Long k, Long s) {
		return new AbstractMap.SimpleEntry<>(k, s);
	}


}
