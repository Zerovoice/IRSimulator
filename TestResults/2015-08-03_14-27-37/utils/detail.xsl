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
				<xsl:for-each select="TEST">
					<br />
					<font color="green">
						<center>
							Use Case Name:
							<xsl:value-of select="@name" />
						</center>
					</font>
					<br />
					<br />
					<TABLE width="90%" cellspacing="1" cellpadding="1" ALIGN="CENTER">
						<TBODY>

							<TR bgcolor="#00e2df">
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</TR>
							<TR bgcolor="#d7e2da">
								<TH>
									<font size="2.5">Index</font>
								</TH>
								<TH>
									<font size="2.5">Key</font>
								</TH>
								<TH>
									<font size="2.5">Name</font>
								</TH>
								<TH>
									<font size="2.5">Interval</font>
								</TH>
								<TH>
									<font size="2.5">Run Result</font>
								</TH>
								<TH>
									<font size="2.5">ScreenShot</font>
								</TH>
							</TR>
							<xsl:apply-templates select="STEPS/STEP" />
							<!--step -->
							<tr />
						</TBODY>
					</TABLE>
					<!--STEPS -->
					<br />
					<br />
					<TABLE width="50%" cellspacing="1" cellpadding="1" ALIGN="CENTER">
						<TBODY>
							<TR bgcolor="#CCFF66">
								<TH>
									<xsl:variable name="logcat">
										<xsl:value-of select="logcat" />
									</xsl:variable>
									<xsl:choose>
										<xsl:when test="$logcat!=''">
											<DIV style="color:red">
												<a href="{$logcat}">Logcat日志</a>
											</DIV>
										</xsl:when>
									</xsl:choose>
								</TH>
								<TH>
									<xsl:variable name="cpuinfo">
										<xsl:value-of select="cpuinfo" />
									</xsl:variable>
									<xsl:choose>
										<xsl:when test="$cpuinfo!=''">
											<DIV style="color:red">
												<a href="{$cpuinfo}">CPU信息</a>
											</DIV>
										</xsl:when>
									</xsl:choose>
								</TH>
							</TR>
						</TBODY>
					</TABLE>
				</xsl:for-each>
			</BODY>
		</HTML>
	</xsl:template>
	<!-- step template -->
	<xsl:template match="STEP">
		<TR>
			<xsl:if test="@runflag='Pass'">
				<xsl:attribute name="style">background-color:#66CC33</xsl:attribute>
			</xsl:if>
			<xsl:if test="@runflag='Fail'">
				<xsl:attribute name="style">background-color:#aa2116</xsl:attribute>
			</xsl:if>
			<xsl:if test="@runflag='Not Run'">
				<xsl:attribute name="style">background-color:#FFFF00</xsl:attribute>
			</xsl:if>
			<TD>
				<font size="2.5">
					<xsl:value-of select="@index" />
				</font>
			</TD>
			<TD>
				<font size="2.5">
					<xsl:value-of select="@key" />
				</font>
			</TD>
			<TD>
				<font size="2.5">
					<xsl:value-of select="@name" />
				</font>
			</TD>
			<TD>
				<font size="2.5">
					<xsl:value-of select="@interval" />
				</font>
			</TD>
			<TD>
				<font size="2.5">
					<xsl:value-of select="@runflag" />
				</font>
			</TD>

			<TD>
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
			</TD>
		</TR>
	</xsl:template>
</xsl:stylesheet>
