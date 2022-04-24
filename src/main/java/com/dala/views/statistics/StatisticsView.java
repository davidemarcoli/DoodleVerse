package com.dala.views.statistics;

import com.dala.data.company.Company;
import com.dala.data.company.CompanyRepository;
import com.dala.data.department.Department;
import com.dala.data.department.DepartmentRepository;
import com.dala.data.person.PersonRepository;
import com.dala.views.MainLayout;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.charts.model.style.SolidColor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;

@PageTitle("Statistics")
@Route(value = "statistics", layout = MainLayout.class)
@PermitAll
@Log4j2
public class StatisticsView extends VerticalLayout {

    private final PersonRepository personRepository;
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;

    private Chart orgChart = null;

    Tab moneyBoxplotTab = new Tab("Money Boxplot");
    Tab orgChartTab = new Tab("Money Boxplot");

    private final VerticalLayout content;

//    Scroller scroller = new Scroller();
//    {
//        scroller.setScrollDirection(Scroller.ScrollDirection.BOTH);
//        scroller.setSizeFull();
//    }



    @Autowired
    public StatisticsView(PersonRepository personRepository, CompanyRepository companyRepository, DepartmentRepository departmentRepository) {
        this.personRepository = personRepository;
        this.companyRepository = companyRepository;
        this.departmentRepository = departmentRepository;

        Tabs tabs = new Tabs(moneyBoxplotTab, orgChartTab);
        tabs.addSelectedChangeListener(event ->
                setContent(event.getSelectedTab())
        );

        content = new VerticalLayout();
        content.setSpacing(false);
        content.setSizeFull();
        setContent(tabs.getSelectedTab());

        add(tabs, content);

        setupPage();
    }

    public void setupPage() {
        setSizeFull();
    }

    private void setContent(Tab tab) {
        content.removeAll();

        if (tab.equals(moneyBoxplotTab)) {
            addMoneyPlot();
        } else if (tab.equals(orgChartTab)) {
            addOrgChart();
        }
    }

    public void addMoneyPlot() {
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
        content.add(moneyBoxplot);
    };

    NodeSeries series = new NodeSeries();
    Node nextNode;
    Node currentNode;
    Department currentDepartment;
    int level = 0;

    public void addOrgChart() {
        if (orgChart != null) {
            content.add(orgChart);
            return;
        }

        Chart orgChart = new Chart(ChartType.ORGANIZATION);
        Configuration conf = orgChart.getConfiguration();
        conf.getChart().setInverted(true);
//        conf.getChart().setHeight("500px");
        conf.getTooltip().setOutside(true);
        conf.setTitle("Organization Chart");
        conf.setScrollbar(new Scrollbar());
        PlotOptionsOrganization plotOptions = new PlotOptionsOrganization();
        plotOptions.setColorByPoint(false);
        plotOptions.setColor(new SolidColor("#007ad0"));

        //Special color for first level
        Level level0 = new Level();
        level0.setLevel(level);
        level0.setColor(new SolidColor("#99AED3"));
        plotOptions.addLevel(level0);
        conf.setPlotOptions(plotOptions);

        series.setName("City");

        ArrayList<Company> companies = new ArrayList<>(companyRepository.findAll());

//        ArrayList<Department> currentLevel = new ArrayList<>();
//        ArrayList<Node> currentLevelNodes = new ArrayList<>();
//        ArrayList<Department> nextLevel = new ArrayList<>();
//        ArrayList<Node> nextLevelNodes = new ArrayList<>();
//
//        for (int nodeListIndex = 0; nodeListIndex <  departments.size() - 1; nodeListIndex++) {
////            try {
////                nodes.get(nodeListIndex + 1);
////            } catch (Exception e) {
////
////            }
//
//            currentLevel = preparedDepartments.get(nodeListIndex);
//            nextLevel = preparedDepartments.get(nodeListIndex + 1);
//
//            if (nodeListIndex == 0) {
////                ArrayList<Department> finalCurrentLevel = new ArrayList<>();
//
////                ArrayList<Node> finalCurrentLevelNodes1 = currentLevelNodes;
////                ArrayList<Node> finalNextLevelNodes1 = nextLevelNodes;
//
//                for (Company company: companies) {
//                    Node companyNode = new Node(company.getCompanyName());
//
//                    for (Department department: company.getDepartments()) {
//                        currentLevelNodes.add(new Node(department.getDepartmentName()));
////                        finalCurrentLevel.add(department);
//                        series.add(companyNode, currentLevelNodes.get(currentLevelNodes.size() - 1));
//                    }
//                }
//
////                companies.forEach(company -> {
////                    Node companyNode = new Node(company.getCompanyName());
////
////                    company.getDepartments().forEach(department -> {
////                        finalCurrentLevelNodes1.add(new Node(department.getDepartmentName()));
//////                        finalCurrentLevel.add(department);
////                        series.add(companyNode, finalNextLevelNodes1.get(finalNextLevelNodes1.size() - 1));
////                    });
////                });
//
//            }
//
////            ArrayList<Department> finalCurrentLevel = currentLevel;
////            ArrayList<Node> finalNextLevelNodes = nextLevelNodes;
////            ArrayList<Node> finalCurrentLevelNodes = currentLevelNodes;
//
//            for (Department department: currentLevel) {
//                for (Department childDepartment: department.getChildDepartments()) {
//                    Node departmentNode = currentLevelNodes.get(currentLevel.indexOf(childDepartment));
//
//                    nextLevelNodes.add(new Node(department.getDepartmentName()));
//                    series.add(departmentNode, nextLevelNodes.get(nextLevelNodes.size() - 1));
//                }
//            }
//
////            currentLevel.forEach(department -> {
////                department.getChildDepartments().forEach(childDepartment -> {
////                    Node departmentNode = finalCurrentLevelNodes.get(finalCurrentLevel.indexOf(childDepartment));
////
////                    finalNextLevelNodes.add(new Node(department.getDepartmentName()));
////                    series.add(departmentNode, finalNextLevelNodes.get(finalNextLevelNodes.size() - 1));
////                });
////            });
//
//            currentLevelNodes = nextLevelNodes;
//            nextLevelNodes = new ArrayList<>();
//
//        }


        for (Company company: companies) {
            nextNode = new Node(String.valueOf(company.getId()), company.getCompanyName());
            level = 0;
            addDepartmentsToSeries(new ArrayList<>(company.getDepartments()));
//            for (Department department: company.getDepartments()) {
//                currentDepartmentNode = new Node(department.getDepartmentName());
//                currentLevelNodes.add(currentDepartmentNode);
//                series.add(companyNode, currentLevelNodes.get(currentLevelNodes.size() - 1));
//
//                nextDepartments = new ArrayList<>(department.getChildDepartments());
//
////                while (!nextDepartments.isEmpty()) {
////                    ArrayList<Department> currentDepartments = (ArrayList<Department>) nextDepartments.clone();
////                    nextDepartments.clear();
////                    for (Department department1: currentDepartments) {
////                        nextDepartmentNode = new Node(department1.getDepartmentName());
////                        series.add(currentDepartmentNode, nextDepartmentNode);
////                        nextDepartments.addAll(department1.getChildDepartments());
////                    }
////                }
//            }

//            level++;
//
//            if (level == 2) {
//                break;
//            }
        }

        orgChart.setSizeFull();

        conf.addSeries(series);

        this.orgChart = orgChart;
        content.add(orgChart);
    }


    public void addDepartmentsToSeries(ArrayList<Department> currentDepartments) {
//        ArrayList<Department> currentDepartments = (ArrayList<Department>) nextDepartments.clone();
//        nextDepartments.clear();

        if (currentDepartments.isEmpty()) return;

        currentNode = nextNode;
        level++;

        for (Department department: currentDepartments) {
            System.out.println(department.toString());
            nextNode = new Node(String.valueOf(department.getId()), department.getDepartmentName());
            System.out.println(currentNode.getName());
            System.out.println(nextNode.getName());
//            nextNode.setLevel(level);
//            nextNode.setLayout(NodeLayout.HANGING);
//            nextNode.setLevel(level);
            series.add(currentNode, nextNode);
            addDepartmentsToSeries(new ArrayList<>(department.getChildDepartments()));
        }

        level--;
    }
}
