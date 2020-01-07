package org.tangdao.common.beetl.handler;

import java.io.IOException;
import java.io.Writer;

import org.beetl.core.ConsoleErrorHandler;
import org.beetl.core.ErrorHandler;
import org.beetl.core.Resource;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.exception.ErrorInfo;

public class LoggerErrorHandler extends ConsoleErrorHandler implements ErrorHandler {
	
	protected static final String VERSION = "";
	protected static final String DEV_MODEL_STYLE = "<style><!-- *{margin:0;padding:0}.main{position:absolute;margin:auto;width:100%;z-index:9999999999999999999;}H1{font-family:Consolas,Arial,sans-serif;color:white;background-color:#a0a0a0;font-size:24px;padding-left:10px}H3{font-family:Consolas,sans-serif;color:#333;font-size:13px;font-weight:lighter;margin-top:5px;font-style:italic}BODY{font-family:Consolas,Arial,sans-serif;color:black;background-color:white}xmp{margin:auto;overflow:auto;width:100%;display: block;white-space: pre-wrap;font-family:simsun,sans-serif;background:white;color:black;font-size:13px;font-weight:lighter;color:#666;line-height:25px;}A{color:black}A.name{color:black}HR{color:#525d76}.t1{margin-top:0;}a.powered:hover{color:#dd1122}--></style>";
	protected static final String DEV_MODEL_ERROR = "<html><head><title>beetlException</title>"
			+ DEV_MODEL_STYLE
			+ "</head><body><div class='main'><h1>[title]</h1><HR size='1' noshade='noshade' class='t1'><xmp style='padding-left:20px'>[message]</xmp><HR size='1' noshade='noshade'><h3>"
			+ VERSION + "</h3><div></body></html>";

	@Override
	public void processExcption(BeetlException e, Writer writer)
	{
		//判断是不是开发者模式,如果不是调用父类方法(默认输出控制台)
		if (!Boolean.valueOf(e.gt.getConf().getProperty("RESOURCE.autoCheck")))
		{
			super.processExcption(e, writer);
		}
		ErrorInfo error = new ErrorInfo(e);
		StringBuilder title = new StringBuilder();
		StringBuilder msg = new StringBuilder();

		if (error.getErrorCode().equals(BeetlException.CLIENT_IO_ERROR_ERROR))
		{
			//不输出详细提示信息
			title = new StringBuilder(">>").append("客户端IO异常:").append(e.resource.getId());
			if (e.getCause() != null)
			{
				msg.append(e.getCause());
			}
			render(writer, title.toString(), msg.toString());
			return;
		}

		int line = error.getErrorTokenLine();

		title = new StringBuilder(">>").append(error.getType()).append(":").append(error.getErrorTokenText())
				.append(" 位于").append(line).append("行").append(" 资源:").append(e.resource.getId());

		if (error.getErrorCode().equals(BeetlException.TEMPLATE_LOAD_ERROR))
		{
			if (error.getMsg() != null)
				msg.append(error.getMsg());
			render(writer, title.toString(), msg.toString());
			return;
		}

		if (e.getMessage() != null)
		{
			msg.append(e.getMessage()).append("\n");
		}

//		ResourceLoader resLoader = e.gt.getResourceLoader();
		//潜在问题，此时可能得到是一个新的模板，不过可能性很小，忽略！
		String content = null;
		Resource res = e.resource;
		//显示前后三行的内容
		int[] range = this.getRange(line);
		try
		{
			content = res.getContent(range[0], range[1]);
		}
		catch (IOException e1)
		{
		}
		if (content != null)
		{
			String[] strs = content.split(e.cr);
			int lineNumber = range[0];
			for (int i = 0; i < strs.length; i++)
			{
				msg.append("" + lineNumber).append("|").append(strs[i].trim()).append("\n");
				lineNumber++;
			}
		}

		if (error.hasCallStack())
		{
			msg.append("  ========================").append("\n");
			msg.append("  调用栈:").append("\n");
			for (int i = 0; i < error.getResourceCallStack().size(); i++)
			{
				msg.append("  " + error.getResourceCallStack().get(i) + " 行：")
						.append(error.getTokenCallStack().get(i).line).append("\n");
			}
			Throwable t = error.getCause();
			if (t != null)
			{
				msg.append(t.toString()).append("\n");
			}
		}

		render(writer, title.toString(), msg.toString());

		try
		{
			writer.flush();
		}
		catch (IOException e1)
		{

		}

	}

	protected void render(Writer w, String title, String msg)
	{
		try
		{
			w.write(DEV_MODEL_ERROR.replace("[title]", title).replace("[message]", msg));
		}
		catch (IOException e)
		{
		}
	}
}
