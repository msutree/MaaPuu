package com.maapuu.mereca.util;

import com.maapuu.mereca.bean.SortModel;

import java.util.Comparator;

public class PinyinComparator implements Comparator<SortModel>
{

	public int compare(SortModel o1, SortModel o2) {
		if (o1.getSortLetters().contains("#")) {
			if (o2.getSortLetters().contains("#")){
				return -1;
			}else {
				return 1;
			}
		} else if (o2.getSortLetters().contains("#")) {
			return -1;
		}else{
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
