package com.senyint.common.kafka;

import java.util.List;

public interface RecordOperator<T> {

    void commit(List<T> records);

}
