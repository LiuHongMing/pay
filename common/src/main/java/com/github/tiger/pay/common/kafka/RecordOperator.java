package com.github.tiger.pay.common.kafka;

import java.util.List;

public interface RecordOperator<T> {

    void commit(List<T> records);

}
