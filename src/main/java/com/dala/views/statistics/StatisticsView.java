package com.dala.views.statistics;

import com.dala.data.person.PersonRepository;
import com.dala.views.MainLayout;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;

@PageTitle("Statistics")
@Route(value = "statistics", layout = MainLayout.class)
@PermitAll
@Log4j2
public class StatisticsView extends VerticalLayout {

    private final PersonRepository personRepository;

    @Autowired
    public StatisticsView(PersonRepository personRepository) {
        this.personRepository = personRepository;

        setupPage();
    }

    public void setupPage() {
        Chart moneyBoxplot = new Chart(ChartType.BOXPLOT);

        // Modify the default configuration a bit
        Configuration conf = moneyBoxplot.getConfiguration();
        conf.setTitle("Money Boxplot");
        conf.getLegend().setEnabled(false);

        // Set median line color and thickness
        PlotOptionsBoxplot plotOptions = new PlotOptionsBoxplot();
        plotOptions.setWhiskerLength("80%");
        plotOptions.setAllowPointSelect(true);
        conf.setPlotOptions(plotOptions);


        DataSeries series = new DataSeries();
        PersonStatAnalysis analysis = new PersonStatAnalysis(personRepository.findAll());
        series.add(new BoxPlotItem(analysis.low(),
                analysis.quartile(25),
                analysis.median(),
                analysis.quartile(75),
                analysis.high()));

        conf.setSeries(series);

        add(moneyBoxplot);

        setSizeFull();
    }
}
