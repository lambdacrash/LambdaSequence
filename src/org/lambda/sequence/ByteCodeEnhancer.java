/* 
 * Distributed as part of LambdaSequence
 * 
 * Author: Brice Onfroy <onfroy.brice@gmail.com>
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 2.1, as 
 * published by the Free Software Foundation.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this software; see the file LICENSE.  If not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 */
package org.lambda.sequence;

import java.io.File;
import java.lang.instrument.IllegalClassFormatException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;

import org.lambda.sequence.util.FileWriterHelper;


import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;

public class ByteCodeEnhancer
{
	private ClassPool pool;
	private String tempDirectory;
	private HashSet<String> urlStrings = new HashSet<String>();
	private ArrayList<String> excludedPackages;
	private ArrayList<String> includedPackages;
	private String dumpFile;

	public byte[] transform(ClassLoader loader, String className,
			byte[] classfileBuffer) throws IllegalClassFormatException 
			{
		String s;
		byte[] result = null;
		CtClass cc = null;
		try 
		{
			if ((loader!=null) && 
				!className.startsWith("org/lambda") &&
				!className.startsWith("sun/reflect"))
			{

				if (loader instanceof URLClassLoader)
				{
					URLClassLoader urlClassLoader = (URLClassLoader)loader;
					URL urls[] = urlClassLoader.getURLs();
					for(URL url:urls)
					{
						String urlString = url.getPath().replaceAll("%20", " ");
						if (!urlStrings.contains(urlString))
						{
							urlStrings.add(urlString);
							pool.appendClassPath(urlString);
						}						
					}
				}
			}
			cc = pool.get(className.replace('/', '.'));
			
			
			
			if(!isRejected(cc.getPackageName().replace('/', '.')) && 
					isRequired(cc.getPackageName().replace('/', '.')))
			{
				
				if (!cc.isInterface() && 
					!cc.isAnnotation() &&
					!cc.isEnum() &&
					!cc.isPrimitive() &&
					!cc.isFrozen() &&
					!cc.isArray())
				{
					System.out.println("Modify class "+cc.getName());
					CtConstructor[] cons = cc.getDeclaredConstructors();
					for(CtConstructor m : cons)
					{
						if(!m.isEmpty() && !m.getLongName().contains("org.lambda."))
						{
							System.out.println("Modify constructor "+m.getLongName());
							s = " I "+m.getLongName()+ " C void Th ";
							m.insertBeforeBody("org.lambda.sequence.util.FileWriterHelper.getInstance().append(System.identityHashCode(this) + \""+s
									+"\" + Thread.currentThread().getId() + \" Caller \" + org.lambda.sequence.util.CallerInfo.getCallerClassName() + " +
												"\" Time \" + org.lambda.sequence.util.CallerInfo.getCallDate());");
							s = " O "+m.getLongName()+ " C void Th ";
							m.insertAfter("org.lambda.sequence.util.FileWriterHelper.getInstance().append(System.identityHashCode(this) + \""+s
									+"\" + Thread.currentThread().getId() + \" Caller \" + org.lambda.sequence.util.CallerInfo.getCallerClassName() + " +
												"\" Time \" + org.lambda.sequence.util.CallerInfo.getCallDate());");
						}
					}

					CtMethod[] methods = cc.getDeclaredMethods();
					for(CtMethod m : methods)
					{
						if(!m.isEmpty() && !m.getLongName().contains("org.lambda."))
						{
							System.out.println("Modify method "+m.getLongName());
							s = " I "+m.getLongName()+" M "+m.getReturnType().getName()+" Th ";

							if(!m.getLongName().contains(".main("))
								m.insertBefore("org.lambda.sequence.util.FileWriterHelper.getInstance().append(System.identityHashCode(this) + \""+s
										+"\" + Thread.currentThread().getId() + \" Caller \" + org.lambda.sequence.util.CallerInfo.getCallerClassName() + " +
												"\" Time \" + org.lambda.sequence.util.CallerInfo.getCallDate());");
							else
								m.insertBefore("org.lambda.sequence.util.FileWriterHelper.getInstance().append(\"-main-"+s
										+"\" + Thread.currentThread().getId() + \" Caller \" + org.lambda.sequence.util.CallerInfo.getCallerClassName() + " +
												"\" Time \" + org.lambda.sequence.util.CallerInfo.getCallDate());");
							
							s = " O "+m.getLongName()+" M "+m.getReturnType().getName()+" Th ";
							if(!m.getLongName().contains(".main("))
								m.insertAfter("org.lambda.sequence.util.FileWriterHelper.getInstance().append(System.identityHashCode(this) + \""+s
										+"\" + Thread.currentThread().getId() + \" Caller \" + org.lambda.sequence.util.CallerInfo.getCallerClassName() + " +
												"\" Time \" + org.lambda.sequence.util.CallerInfo.getCallDate());");
							else
								m.insertAfter("org.lambda.sequence.util.FileWriterHelper.getInstance().append(\"-main-"+s
										+"\" + Thread.currentThread().getId() + \" Caller \" + org.lambda.sequence.util.CallerInfo.getCallerClassName() + " +
												"\" Time \" + org.lambda.sequence.util.CallerInfo.getCallDate());");
							
						}
					}
					cc.writeFile(tempDirectory);
					result =  cc.toBytecode();
				}
			}
		} 
		catch(Throwable t)
		{
			System.err.println("cannot enhance "+className);
			t.printStackTrace();
			result = classfileBuffer;
		}
		finally
		{
			try
			{
				if (cc!=null)cc.detach();
			}
			catch(Throwable t){}
		}
		return result;
	}
	
	private boolean isRejected(String packaze)
	{
		if(excludedPackages.isEmpty()) return false;
		for(String s : excludedPackages)
			if(packaze.contains(s))
				return true;
		return false;
	}
	
	private boolean isRequired(String packaze)
	{
		if(includedPackages.isEmpty()) return true;
		for(String s : includedPackages)
			if(packaze.contains(s))
				return true;
		return false;
	}
	
	public ByteCodeEnhancer(String agentArgs)
	{
		pool = ClassPool.getDefault();
		tempDirectory = System.getProperty("user.home")+File.separator+".javassist";
		excludedPackages = new ArrayList<String>();
		includedPackages = new ArrayList<String>();
		dumpFile = System.getProperty("user.home")+File.separator+"call_trace_"+System.currentTimeMillis()+".lbds";
		String[] temp = agentArgs.split(",");
		for(String s : temp)
		{
			System.out.println(s);
			if(s.contains("file="))
				this.dumpFile = s.split("=")[1];
			else if(s.startsWith("-"))
				excludedPackages.add(s.substring(1));
			else if(s.startsWith("+"))
				includedPackages.add(s.substring(1));
		}
		
		for(String s : includedPackages)
			System.out.println("+ "+s);
		for(String s : excludedPackages)
			System.out.println("- "+s);

		System.out.println("*************************************************");
		System.out.println("* dump file: "+this.dumpFile+"");
		System.out.println("*************************************************");
		
		FileWriterHelper.getInstance(this.dumpFile);
		
		File file = new File(tempDirectory);
		if (!file.exists()) file.mkdir();
	}
}
