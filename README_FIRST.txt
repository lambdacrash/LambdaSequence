Licence
-------

 This library is free software; you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License version 2.1, as 
 published by the Free Software Foundation.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 USE OR OTHER DEALINGS IN THE SOFTWARE.

 The above copyright notice and this permission notice shall be included
 in all copies or substantial portions of the Software.

 You should have received a copy of the GNU Lesser General Public License
 along with this software; see the file LICENSE.  If not, write to the
 Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 Boston, MA 02111-1307, USA.


Introduction
------------

LambdaSequence is a tool meant to draw the method calls sequence within your applications. It provides a java agent that
you must configure within your application launch VM parameters. The java agent option is only 
available since JVM >= 1.5


Launch the agent
----------------

The agent tries enhancing all classes to log all method calls and return. 

It does NOT work with any of the class loaded by the SystemClassLoader, hence all JVM classes such as "String" are not checked.

To run an agent which will open a dialog monitor by itself, add the following JVM parameters to your application:

-javaagent:/path/to/the/agent/lambdaSequence.jar=[options]

So, the available agent options are:
* +<package_name> : the specified package will be traced
* -<package_name> : the specified package will be not traced
* file=/a/path/to/the_dump_file.lbds : the log will be saved in this specified file
If no file is specified, the default output will in your home directory nammed "call_trace-<timestamp>.lbds"


Example :
  -javaagent:/tmp/lambdaSequence.jar=+my.package,file=/tmp/dump.lbds
  Will imply the trace of only one package which nammed "my.package" and the log will be saved in "/tmp/dump.lbds"

Launch the display engine
-------------------------
In order to launch this software to display your diagram, use this command line:
  java -jar lambdaSequence.jar /your/dump_file.lbds

Special commmands:
If you want to save your diagram image into a file, juste press the [s] key on your keyboard and specify the file name.
Example: 
  /tmp/diagram.bmp

Color code:
* Constructor calls are displayed in red
* Method calls are displayed in blue
* Return call are displayed in grey
* Each thread is represented by a colored raw which contains the id of the thread ("Th id 12")

Call representation:
- Class A to class B call
  hash code - returning_type
----------------------------------------------------->
  method name

- Self call:
  hash code - returning type
><
  method name

NOTES
-----
The source code of this software is not well documented yet. This is an alpha version distributed into order to get returns
about bugs, desired features, ...



CHANGE LOG:
----------
12/01/2010 :
    - complete this readme
    - fix bug into build.xml (javassist lib is missing) // please no comment :-P
