package com.hisense.autotest.chart;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class RealTimeChartRCPU extends ChartPanel {
	private static Logger logger = Logger.getLogger(RealTimeChartRCPU.class);

	private static final long serialVersionUID = 1L;

	private static TimeSeries timeSeries;
	private Millisecond mili;

	public void addSery(float ratio) {
		// timeSeries.clear();
		mili = new Millisecond();
		timeSeries.addOrUpdate(mili, ratio);
		logger.debug("RCPU:       mili = " + mili + "           ratio = " + ratio);
	}

	public RealTimeChartRCPU(String yaxisName, Double timeLen) {
		super(createChart(yaxisName, timeLen));
	}

	@SuppressWarnings("deprecation")
	private static JFreeChart createChart(String yaxisName, Double timeLen) {
		// 创建时序图对象
		timeSeries = new TimeSeries("CPU ratio", Millisecond.class);
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection(timeSeries);
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("CPU", null, yaxisName, timeseriescollection, true, true, false);
		XYPlot xyplot = jfreechart.getXYPlot();
		// 纵坐标设定
		ValueAxis valueaxis = xyplot.getDomainAxis();
		// 自动设置数据轴数据范围
		valueaxis.setAutoRange(true);
		// 数据轴固定数据范围 10分钟
		// valueaxis.setFixedAutoRange(600000D);
		logger.debug("折线图时间轴设定：" + timeLen);
		valueaxis.setFixedAutoRange(timeLen);

		valueaxis = xyplot.getRangeAxis();
		// valueaxis.setRange(0.0D,1.0D);

		return jfreechart;
	}

	// private static float randomNum() {
	// Random rnd = new Random();
	// float val = rnd.nextFloat();
	// val = Float.parseFloat(String.format("%.3f", val));
	// System.out.println(val);
	// return val;
	// }

}
