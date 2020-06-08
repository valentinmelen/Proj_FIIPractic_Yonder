package fii.practic.health.service.impl;

import fii.practic.health.service.MathService;
import org.springframework.stereotype.Service;

@Service
public class MathServiceImpl implements MathService {

    @Override
    public int computeSum(int x, int y) {
        return x + y;
    }
}
