package com.matcha.closure;

import com.matcha.atomic.app.People;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Created by Administrator on 2016/12/20.
 */
public interface ISetter
{
    People setter(AtomicStampedReference<People> oldPeopleReference);
}
