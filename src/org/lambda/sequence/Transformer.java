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

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class Transformer implements ClassFileTransformer
{
	private Hashtable<ClassLoader, ByteCodeEnhancer> enhancers = new Hashtable<ClassLoader, ByteCodeEnhancer>();
	private Set<ClassLoader> appendedLoaders = new HashSet<ClassLoader>();
	private String agentArgs;
	private URL lambdaSequenceURL;

	public Transformer(String agentArgs)
	{
		this.agentArgs = agentArgs;

		ClassLoader loader = getClass().getClassLoader();
		System.out.println("Default output file in /tmp/calltrace ... .txt");
		if (loader instanceof URLClassLoader)
		{
			URLClassLoader urlClassLoader = (URLClassLoader) loader;
			URL urls[] = urlClassLoader.getURLs();
			for (URL url : urls)
			{
				String urlString = url.getPath().replaceAll("%20", " ");
				System.out.println("url:" + urlString);
				if (urlString.indexOf("lambdaSequence.jar") > -1)
					lambdaSequenceURL = url;
			}
		}

	}

	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException
	{

		byte[] result = classfileBuffer;
		try
		{
			if (loader != null)
			{
				ByteCodeEnhancer enhancer = enhancers.get(loader);
				if (!appendedLoaders.contains(loader))
				{
					System.out.println("loader: " + loader);
					if (lambdaSequenceURL != null)
					{
						enhanceClassLoader(loader, lambdaSequenceURL);
						appendedLoaders.add(loader);
					}
				}

				if (enhancer == null)
				{
					try
					{
						Class<?> clazz = Class.forName("org.lambda.sequence.ByteCodeEnhancer", true, loader);
						Constructor<?> cons = clazz.getConstructor(new Class[] { String.class });
						Object instance = cons
								.newInstance(new Object[] { agentArgs });
						Method method = clazz.getMethod("transform",
								new Class[] { ClassLoader.class, String.class,
										byte[].class });
						result = (byte[]) method.invoke(instance, new Object[] 
                                         	{loader, className, classfileBuffer });
						enhancer = (ByteCodeEnhancer) instance;
					} 
					catch (Throwable t)
					{
						System.err.println("could not instantiate dynamically the enhancer for class "
										+ className
										+ " with ClassLoader "
										+ loader);
					}
					System.out.println("New enhancer for class loader " + loader);
					enhancers.put(loader, enhancer);
				} 
				else
					result = enhancer.transform(loader, className, classfileBuffer);
			}
		} 
		catch (Throwable t)
		{
			System.err.println("could not even instantiate the enhancer for class loader "
							+ getClass().getClassLoader());
			System.err.println("no enhancement possible for class: "
							+ className);
		}
		return result;
	}

	private void enhanceClassLoader(ClassLoader loader, URL url)
	{
		boolean done = false;
		while (!done)
		{
			try
			{
				if (loader instanceof URLClassLoader)
				{
					URLClassLoader urlClassLoader = (URLClassLoader) loader;
					if (!contains(urlClassLoader, url))
					{
						Method addUrlMethod = this
								.getAddURLMethod(urlClassLoader);
						if (addUrlMethod != null)
						{
							addUrlMethod.invoke(loader, new Object[] { url });
							done = true;
							System.out.println("enhanced " + loader);
						} 
						else
						{
							System.err.println("addURL method not found in URLClassLoader");
						}
					} 
					else
					{
						System.err.println("loader " + loader + " already knows " + url);
						done = true;
					}
				} 
				else
				{
					loader = loader.getParent();
					if (loader == null)
						done = true;
				}
			} 
			catch (Throwable t)
			{
				done = true;
			}
		}
	}

	private boolean contains(URLClassLoader urlClassLoader, URL url)
	{
		boolean result = false;
		URL urls[] = urlClassLoader.getURLs();
		if (urls != null)
		{
			for (int i = 0; i < urls.length; i++)
			{
				if (url.getPath().equals(urls[i].getPath()))
				{
					result = true;
				}
			}
		}
		return result;
	}

	private Method getAddURLMethod(URLClassLoader urlClassLoader)
	{
		Method addUrlMethod = null;
		try
		{
			Class<?> urlClassLoaderClass = urlClassLoader.getClass();
			while (!urlClassLoaderClass.getName().equals("java.net.URLClassLoader"))
				urlClassLoaderClass = urlClassLoaderClass.getSuperclass();
			addUrlMethod = urlClassLoaderClass.getDeclaredMethod("addURL", new Class[] { URL.class });
			addUrlMethod.setAccessible(true);
		} catch (Throwable ex)
		{
			ex.printStackTrace();
		}
		return addUrlMethod;
	}
}
