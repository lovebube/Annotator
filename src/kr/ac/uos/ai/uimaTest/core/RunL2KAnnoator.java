package kr.ac.uos.ai.uimaTest.core;

import java.io.File;
import java.util.HashMap;

import org.apache.uima.aae.client.UimaAsynchronousEngine;
import org.apache.uima.adapter.jms.client.BaseUIMAAsynchronousEngine_impl;

import activemq.Receiver;

public class RunL2KAnnoator {
	private final UimaAsynchronousEngine						uimaEngine;
	private final HashMap<String, Object>						applicationSetting;
	
	private final String UIMA_HOME								= "/opt/exobrain/apache-uima-as-2.6.0/";
	private final String HOME									= System.getenv("HOME");	
	public RunL2KAnnoator() {
		uimaEngine = new BaseUIMAAsynchronousEngine_impl();
		applicationSetting = new HashMap<String, Object>();
//		setReceiver();
		init();
		
	}
	
	private void init() {
		applicationSetting.put(UimaAsynchronousEngine.DD2SpringXsltFilePath, UIMA_HOME+"/bin/dd2spring.xsl");
		applicationSetting.put(UimaAsynchronousEngine.SaxonClasspath, "file:" + UIMA_HOME+"/saxon/saxon8.jar");

		try {
			uimaEngine.deploy(HOME+"/uos/annotator/L2KDeployment.xml", applicationSetting);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
	}
	
	private void setReceiver() {
		Receiver rcv = new Receiver();
		rcv.setQueueName("SysLogMsg");
		rcv.init();
		Thread rcvThread = new Thread(rcv);
		rcvThread.start();
	}

	public static void main(final String[] args) {
		new RunL2KAnnoator();
	}
}
