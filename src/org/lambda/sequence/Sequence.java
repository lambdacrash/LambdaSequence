package org.lambda.sequence;

import java.lang.instrument.Instrumentation;

public class Sequence 
{
	public static void premain(String agentArgs,Instrumentation instr)	throws Exception 
	{
		instr.addTransformer(new Transformer(agentArgs));
	}
}
