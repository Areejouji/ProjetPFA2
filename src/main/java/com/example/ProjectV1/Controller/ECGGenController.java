package com.example.ProjectV1.Controller;

import com.example.ProjectV1.Service.RecordingService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class ECGGenController {
    @Autowired
    private RecordingService recordingService;

    @PostMapping("/ecg")
    public ResponseEntity<byte[]> generateECG(@RequestBody ArrayList<Double> ecgData) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < ecgData.size(); i++) {
            dataset.addValue(ecgData.get(i), "ECG", Integer.toString(i));
        }

        JFreeChart chart = ChartFactory.createLineChart("ECG Representation", "Time", "Voltage", dataset, PlotOrientation.VERTICAL, true, true, true );
        chart.getCategoryPlot().getRenderer().setSeriesPaint(0, new Color(0, 0, 255));
        chart.getCategoryPlot().setBackgroundPaint(Color.WHITE);
        /*chart.addChangeListener();{
            @Override
            public void chartMouseClicked(ChartMouseEvent event) {
                // do nothing
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent event) {
                // get the chart and plot
                JFreeChart chart = event.getChart();
                CategoryPlot plot = (CategoryPlot) chart.getPlot();

                // get the mouse coordinates
                Point2D mousePoint = event.getTrigger().getPoint();
                ChartRenderingInfo chartInfo = chart.getRenderingInfo();
                Rectangle2D plotArea = chartInfo.getPlotInfo().getDataArea();
                double chartX = plot.getDomainAxis().java2DToValue(mousePoint.getX(), plotArea, plot.getDomainAxisEdge());
                double chartY = plot.getRangeAxis().java2DToValue(mousePoint.getY(), plotArea, plot.getRangeAxisEdge());

                // set the crosshair values
                CrosshairOverlay crosshairOverlay = new CrosshairOverlay();
                Crosshair xCrosshair = new Crosshair(chartX);
                xCrosshair.setPaint(Color.RED);
                Crosshair yCrosshair = new Crosshair(chartY);
                yCrosshair.setPaint(Color.RED);
                crosshairOverlay.addDomainCrosshair(xCrosshair);
                crosshairOverlay.addRangeCrosshair(yCrosshair);
                plot.addOverlay(crosshairOverlay);
                xCrosshair.setValue(chartX);
                yCrosshair.setValue(chartY);
            }
        });*/
        byte[] chartBytes = null;
        try {
            chartBytes = ChartUtilities.encodeAsPNG(chart.createBufferedImage(640, 480));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(chartBytes, headers, HttpStatus.OK);
    }
}