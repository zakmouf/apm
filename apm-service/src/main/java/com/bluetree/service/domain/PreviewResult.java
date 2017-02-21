package com.bluetree.service.domain;

import java.util.ArrayList;
import java.util.List;

import com.bluetree.domain.ObjectSupport;

public class PreviewResult extends ObjectSupport {

    private static final long serialVersionUID = 1L;

    private List<CombinedMeasure> combinedMeasures = new ArrayList<CombinedMeasure>();

    public List<CombinedMeasure> getCombinedMeasures() {
	return combinedMeasures;
    }

    public void addCombinedMeasure(CombinedMeasure combinedMeasure) {
	combinedMeasures.add(combinedMeasure);
    }

}
