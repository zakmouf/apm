package com.bluetree.service;

import com.bluetree.service.domain.OptimizeConfig;
import com.bluetree.service.domain.OptimizeException;
import com.bluetree.service.domain.OptimizeResult;

public interface OptimizeService {

    OptimizeResult process(OptimizeConfig config) throws OptimizeException;

}
