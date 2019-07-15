/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.controller.servicePO;

import com.mileset.neeleshengineers.modal.ServicePO;
import java.util.ArrayList;

/**
 *
 * @author rahul
 */
public interface servicePOCallBack {

    void onCallback(ArrayList<ServicePO> value);

    void recordNotPresent();
}
