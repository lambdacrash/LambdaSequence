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
package org.lambda.sequence.util;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterHelper 
{
	private static FileWriterHelper instance;
	private FileWriter fw;
	
	private FileWriterHelper(String file)
	{
		try 
		{
			this.fw = new FileWriter(file);
		} 
		catch (IOException e) 
		{}
	}

	public static synchronized FileWriterHelper getInstance()
	{
		if(instance == null)
			instance = new FileWriterHelper("/tmp/calltrace_"+(System.currentTimeMillis())+".txt");
		return instance;
	}
	
	public static synchronized FileWriterHelper getInstance(String file)
	{
		if(instance == null)
			instance = new FileWriterHelper(file);
		return instance;
	}
	
	public synchronized void append(String s)
	{
		try 
		{
			fw.append(s+"\n");
			fw.flush();
		} 
		catch (IOException e){e.printStackTrace();}
	}
	
	public void finalize()
	{
		this.close();
	}
	
	public void close()
	{
		try 
		{
			fw.close();
		} 
		catch (IOException e){e.printStackTrace();}
	}
}
