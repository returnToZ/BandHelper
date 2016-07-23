/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.achartengine.chartdemo.demo.chart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

/**
 * Project status demo chart.
 */
public class ProjectStatusChart extends AbstractDemoChart {
    /**
     * Returns the chart name.
     *
     * @return the chart name
     */
    public String getName() {
        return "Project tickets status";
    }

    /**
     * Returns the chart description.
     *
     * @return the chart description
     */
    public String getDesc() {
        return "The opened tickets and the fixed tickets (time chart)";
    }

    /**
     * Executes the chart demo.
     *
     * @param context the context
     * @return the built intent
     */
    public Intent execute(Context context) {
        String[] titles = new String[]{"New tickets", "Fixed tickets"};
        //List<Date[]> dates = new ArrayList<Date[]>();
        List<double[]> values = new ArrayList<double[]>();
        List<double[]> data = new ArrayList<double[]>();
        int length = titles.length;
        /*
        for (int i = 0; i < length; i++)
        {
            dates.add(new Date[12]);
            dates.get(i)[0] = new Date(108, 9, 1);
            dates.get(i)[1] = new Date(108, 9, 8);
            dates.get(i)[2] = new Date(108, 9, 15);
            dates.get(i)[3] = new Date(108, 9, 22);
            dates.get(i)[4] = new Date(108, 9, 29);
            dates.get(i)[5] = new Date(108, 10, 5);
            dates.get(i)[6] = new Date(108, 10, 12);
            dates.get(i)[7] = new Date(108, 10, 19);
            dates.get(i)[8] = new Date(108, 10, 26);
            dates.get(i)[9] = new Date(108, 11, 3);
            dates.get(i)[10] = new Date(108, 11, 10);
            dates.get(i)[11] = new Date(108, 11, 17);
        }
        */
        for (int i = 0; i < length; i++)
        {
            data.add(new double[12]);
            data.get(i)[0] = 1;
            data.get(i)[1] = 2;
            data.get(i)[2] = 3;
            data.get(i)[3] = 4;
            data.get(i)[4] = 5;
            data.get(i)[5] = 6;
            data.get(i)[6] = 7;
            data.get(i)[7] = 8;
            data.get(i)[8] = 9;
            data.get(i)[9] = 10;
            data.get(i)[10] = 11;
            data.get(i)[11] = 12;
        }
        values.add(new double[]{142, 123, 142, 152, 149, 122, 110, 120, 125, 155, 146, 150});
        values.add(new double[]{102, 90, 112, 105, 125, 112, 125, 112, 105, 115, 116, 135});
        length = values.get(0).length;
        int[] colors = new int[]{Color.BLUE, Color.GREEN};
        PointStyle[] styles = new PointStyle[]{PointStyle.CIRCLE, PointStyle.POINT};//调整点的style
        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
        setChartSettings(renderer, "Project work status", "Date", "Tickets", data.get(0)[0],
                data.get(0)[11], 50, 190, Color.GRAY, Color.LTGRAY);
        renderer.setXLabels(0);//x轴标签大小
        renderer.setYLabels(10);//y轴标签大小
        renderer.addYTextLabel(100, "test");//Y轴值为100处的字为test
        length = renderer.getSeriesRendererCount();//渲染器的个数
        for (int i = 0; i < length; i++)
        {
            XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);//获取第i个渲染器
            seriesRenderer.setDisplayChartValues(true);//是否在图上显示具体数值
            seriesRenderer.setFillPoints(true);//将点设置为实心的点
            seriesRenderer.setDisplayChartValuesDistance(30);//必须设置距离 因为默认距离是100 假如设置为100则无法显示所有的点
        }
        renderer.setXRoundedLabels(false);
        //return ChartFactory.getTimeChartIntent(context, buildDateDataset(titles, dates, values),
        //    renderer, "MM/dd/yyyy");

        return ChartFactory.getLineChartIntent(context, buildDataset(titles, data, values),renderer,"HelloWorld");
    }

}
