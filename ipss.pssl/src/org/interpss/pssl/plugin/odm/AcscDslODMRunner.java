/*
  * @(#)AclfDslODMRunner.java   
  *
  * Copyright (C) 2006-2011 www.interpss.com
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
  * @Date 12/15/2011
  * 
  *   Revision History
  *   ================
  *
  */

package org.interpss.pssl.plugin.odm;

import org.apache.commons.math3.complex.Complex;
import org.ieee.odm.schema.AcscBaseFaultXmlType;
import org.ieee.odm.schema.AcscBranchFaultXmlType;
import org.ieee.odm.schema.AcscBusFaultXmlType;
import org.ieee.odm.schema.AcscFaultAnalysisXmlType;
import org.ieee.odm.schema.AcscFaultCategoryEnumType;
import org.ieee.odm.schema.AcscFaultTypeEnumType;
import org.ieee.odm.schema.ComplexXmlType;
import org.interpss.pssl.simu.IpssAcsc;
import org.interpss.pssl.simu.IpssAcsc.FaultAlgoDSL;

import com.interpss.common.exp.InterpssException;
import com.interpss.core.acsc.AcscNetwork;
import com.interpss.core.acsc.fault.SimpleFaultCode;
import com.interpss.core.datatype.IFaultResult;

/**
 * Acsc Dsl ODM runner implementation
 * 
 * @author mzhou
 *
 */
public class AcscDslODMRunner {
	private AcscNetwork net;
	
	/**
	 * constructor
	 * 
	 * @param net AcscNetwork object
	 */
	public AcscDslODMRunner(AcscNetwork net) {
		this.net = net;
	}
	
	/**
	 * run the acsc analysis case and return the analysis results
	 * 
	 * @param acscCaseXml
	 * @return
	 */
	public IFaultResult runAcsc(AcscFaultAnalysisXmlType acscCaseXml)  throws InterpssException {
  		FaultAlgoDSL algo = IpssAcsc.createAcscAlgo(this.net);
  		
		AcscBaseFaultXmlType faultXml = acscCaseXml.getAcscFault().getValue();
  		if (faultXml.getFaultType() == AcscFaultTypeEnumType.BUS_FAULT) {
  			AcscBusFaultXmlType busFault = (AcscBusFaultXmlType)faultXml;
  	  		algo.createBusFault(busFault.getRefBus().getBusId())
  	  			.faultCode(mapCode(busFault.getFaultCategory()))
  	  			.zLGFault(toComplex(busFault.getZLG()))
  	  			.zLLFault(toComplex(busFault.getZLL()))
  	  			.calculateFault();
  	  		return algo.getResult();
  		}
  		else if (faultXml.getFaultType() == AcscFaultTypeEnumType.BRANCH_FAULT) {
  			AcscBranchFaultXmlType braFault = (AcscBranchFaultXmlType)faultXml;
  	  		algo.createBranchFault(braFault.getRefBranch().getFromBusId(), braFault.getRefBranch().getToBusId(), braFault.getRefBranch().getCircuitId())
  	  			.faultCode(mapCode(braFault.getFaultCategory()))
  	  			.zLGFault(toComplex(braFault.getZLG()))
  	  			.zLLFault(toComplex(braFault.getZLL()))
  	  			.distance(braFault.getDistance())
  	  			.calculateFault();
  	  		return algo.getResult();
  		}
  		else if (faultXml.getFaultType() == AcscFaultTypeEnumType.BRANCH_OUTAGE) {
  			// TODO
  		}
		return null;
	}
	
	private SimpleFaultCode mapCode(AcscFaultCategoryEnumType caty) {
		return caty == AcscFaultCategoryEnumType.FAULT_3_PHASE ? SimpleFaultCode.GROUND_3P : 
					(caty == AcscFaultCategoryEnumType.LINE_LINE_TO_GROUND ? SimpleFaultCode.GROUND_LLG :
						(caty == AcscFaultCategoryEnumType.LINE_TO_GROUND ? 
								SimpleFaultCode.GROUND_LG : SimpleFaultCode.GROUND_LL));
		
	}
	
	private Complex toComplex(ComplexXmlType x) {
		return new Complex(x.getRe(), x.getIm());
	}
}
