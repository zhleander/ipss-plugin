/*
 * @(#)BaseJSONBean.java   
 *
 * Copyright (C) 2008-2013 www.interpss.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU LESSER GENERAL PUBLIC LICENSE
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * @Author Mike Zhou
 * @Version 1.0
 * @Date 01/10/2013
 * 
 *   Revision History
 *   ================
 *
 */
package org.interpss.datamodel.bean.aclf.adj;

import java.util.List;

import org.interpss.datamodel.bean.BaseJSONBean;
import org.interpss.datamodel.bean.datatype.BranchValueBean;
import org.interpss.numeric.util.NumericUtil;

/**
 * Bean class for storing Aclf two winding branch object info
 * 
 * @author sHou
 *
 */
public class TapControlBean extends BaseJSONBean {
	
	public static enum TapControlModeBean {Bus_Voltage, Mva_Flow, MW_Flow};  
	public static enum TapControlTypeBean {Point_Control, Range_Control};
	
	/*public BranchValueBean 
			ratio = new BranchValueBean(1.0,1.0),			// xfr branch turn ratio, it is assumed on the from bus side per PSSE
			ang = new BranchValueBean(0.0,0.0);				// PsXfr shifting angle, in rad, it is assumed on the from bus side per PSSE
	*/
	public TapControlModeBean controlMode = TapControlModeBean.Bus_Voltage;	// control mode
	public TapControlTypeBean controlType = TapControlTypeBean.Point_Control; // control type
	
	public int status = 1;		// tap control status
	
	public double 
		maxTap,					// max tap
		minTap,					// min tap
		upperLimit,				// tap control target upper limit (range control)
		lowerLimit,				// tap control target lower limit (range control)
		desiredControlTarget,	// tap control targeted value (point control)
		stepSize;				// tap control step size
	
	public String controlledBusId;		// controlled bus number
	
	public boolean 
		measuredOnFromSide,		// mvar flow is measured on from side
	    controlOnFromSide;		// control is applied on from side	
		
	public int steps;						// tap control steps		
	
	public TapControlBean() {}
	
	@Override public int compareTo(BaseJSONBean b) {
		int eql = super.compareTo(b);
		
		TapControlBean bean = (TapControlBean)b;

		String str = "ID: " + this.id + " TapControlBean.";
		
		/*if (this.ratio.compareTo(bean.ratio) != 0) {
			logCompareMsg(str + "ratio is not equal");	eql = 1; }

		if (this.ang.compareTo(bean.ang) != 0) {
			logCompareMsg(str + "ang is not equal");	eql = 1; }*/
		
		if (this.status != bean.status) {
			logCompareMsg(str + "status is not equal, " + this.status + ", " + bean.status); eql = 1; }
		
		// compare double
		if (!NumericUtil.equals(this.upperLimit, bean.upperLimit, PU_ERR)) {
			logCompareMsg(str + "upperLimit is not equal, " + this.upperLimit + ", " + bean.upperLimit); eql = 1; }
		if (!NumericUtil.equals(this.lowerLimit, bean.lowerLimit, PU_ERR)) {
			logCompareMsg(str + "lowerLimit is not equal, " + this.lowerLimit + ", " + bean.lowerLimit); eql = 1;	}
		if (!NumericUtil.equals(this.stepSize, bean.stepSize, ANG_ERR)) {
			logCompareMsg(str + "stepSize is not equal, " + this.stepSize + ", " + bean.stepSize); eql = 1; }
		if (!NumericUtil.equals(this.desiredControlTarget, bean.desiredControlTarget, ANG_ERR)) {
			logCompareMsg(str + "desiredVoltage is not equal, " + this.desiredControlTarget + ", " + bean.desiredControlTarget); eql = 1; }
		
		
		if (this.controlMode != bean.controlMode) {
			logCompareMsg(str + "control mode is not equal, " + this.controlMode + ", " + bean.controlMode); eql = 1; }
		if (this.controlType != bean.controlType) {
			logCompareMsg(str + "control type is not equal, " + this.controlType + ", " + bean.controlType); eql = 1; }
		
		if (!this.controlledBusId.equals(bean.controlledBusId)) {
			logCompareMsg(str + "controlledBusNumber is not equal, " + this.controlledBusId + ", " + bean.controlledBusId); eql = 1; }
		
		if (this.steps != bean.steps) {
			logCompareMsg(str + "steps is not equal, " + this.steps + ", " + bean.steps); eql = 1; }
		if (this.measuredOnFromSide != bean.measuredOnFromSide) {
			logCompareMsg(str + "measuredOnFromSide is not equal, " + this.measuredOnFromSide + ", " + bean.measuredOnFromSide); eql = 1; }
		if (this.controlOnFromSide != bean.controlOnFromSide) {
			logCompareMsg(str + "controlOnFromSide is not equal, " + this.controlOnFromSide + ", " + bean.controlOnFromSide); eql = 1; }

		return eql;
	}	
	
	@Override
	public boolean validate(List<String> msgList) { return true; }
}
