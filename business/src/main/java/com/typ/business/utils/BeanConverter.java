package com.typ.business.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public final class BeanConverter {

    private BeanConverter() {
    }

    public static <S, T> T convert(S source, Supplier<T> targetSupplier) {
        if (source == null) {
            return null;
        }
        T target = targetSupplier.get();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static <S, T> List<T> convertList(List<S> sourceList, Supplier<T> targetSupplier) {
        if (sourceList == null) {
            return null;
        }
        List<T> result = new ArrayList<>(sourceList.size());
        for (S s : sourceList) {
            result.add(convert(s, targetSupplier));
        }
        return result;
    }

    public static <S, T> IPage<T> convertPage(IPage<S> sourcePage, Supplier<T> targetSupplier) {
        if (Objects.isNull(sourcePage)) {
            return null;
        }
        IPage<T> targetPage = new Page<>(sourcePage.getCurrent(), sourcePage.getSize());
        targetPage.setRecords(convertList(sourcePage.getRecords(), targetSupplier));
        return targetPage;
    }
}


