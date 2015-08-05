<!DOCTYPE xsl:stylesheet [ <!ENTITY nbsp "&#160;"> ]>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="gb2312" indent="yes" />
	<xsl:template match="/">
		<HTML>

			<BODY topmargin="0">
				<table id="Table_01" width="100%" height="49" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="687" height="49">
							<table width="687" height="49" background='img/top_01.jpg'>
								<tr>
									<td></td>
								</tr>
							</table>
						</td>
						<td width="100%" background='img/top_02.jpg' align="right">
							<font
								style="font-family:Arial;font-size:12px;position:relative;right:130px;">
								Smart Automated Regression Testing
								<BR />
								测试规划及自动化组
							</font>
						</td>
					</tr>
				</table>

				<xsl:for-each select="TestResultSummary">
					<br />
					<font color="green">
						<center>
							测试结果概要
						</center>
					</font>
					<br />

					<TABLE width="90%" cellspacing="1" cellpadding="1" ALIGN="CENTER">
						<TBODY>

							<TR bgcolor="#00e2df">
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</TR>
							<TR bgcolor="#d7e2da">
								<TH>
									<font size="2.5">用例文件</font>
								</TH>
								<TH width='80'>
									<font size="2.5">执行结果</font>
								</TH>
								<TH>
									<font size="2.5">错误信息</font>
								</TH>
								<TH>
									<font size="2.5">截图</font>
								</TH>
								<TH>
									<font size="2.5">详细文件</font>
								</TH>
							</TR>

							<xsl:for-each select="TestCases/TEST">
								<TR>
									<xsl:if test="@runflag='Pass'">
										<xsl:attribute name="style">
											background-color:#66CC33
												</xsl:attribute>
									</xsl:if>

									<xsl:if test="@runflag='Fail'">
										<xsl:attribute name="style">
											background-color:#aa2116
												</xsl:attribute>
									</xsl:if>

									<xsl:if test="@runflag='Not Run'">
										<xsl:attribute name="style">
											background-color:#FFFF00
												</xsl:attribute>
									</xsl:if>
									<TD>
										<font size="2.5">
											<xsl:value-of select="@name" />
										</font>
									</TD>
									<TD>
										<font size="2.5">
											<xsl:value-of select="@runflag" />
										</font>
									</TD>
									<TD>
										<font size="2.5">
											<xsl:value-of select="@testrstmsg" />
										</font>
									</TD>
									<TD>
										<font size="2.5">
											<xsl:for-each select="imgsrc">
												<xsl:element name="a">
													<xsl:attribute name="href">
														<xsl:value-of select="." />
													</xsl:attribute>
													<xsl:element name="image">
														<xsl:attribute name="width">120</xsl:attribute>
														<xsl:attribute name="height">100</xsl:attribute>
														<xsl:attribute name="src"><xsl:value-of select="." /></xsl:attribute>
													</xsl:element>
												</xsl:element>
											</xsl:for-each>
										</font>
									</TD>
									<TD>
										<font size="2.5">
											<xsl:variable name="href"><xsl:value-of select="@detailfile"/></xsl:variable>
											<a href="{$href}"><xsl:value-of select="@detailfile" /></a>
										</font>
									</TD>
								</TR>
							</xsl:for-each>

							<!--step -->
						</TBODY>
					</TABLE>

					<br />
					<br />
				</xsl:for-each>

			</BODY>
		</HTML>
	</xsl:template>
</xsl:stylesheet>
