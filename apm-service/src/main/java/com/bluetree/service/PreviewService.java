package com.bluetree.service;

import com.bluetree.service.domain.PreviewException;
import com.bluetree.service.domain.PreviewConfig;
import com.bluetree.service.domain.PreviewResult;

public interface PreviewService {

    PreviewResult process(PreviewConfig config) throws PreviewException;

}
