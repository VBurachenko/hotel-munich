function autoClick(){
    document.getElementById("defaultOpen").click();
}

function openTable(tableId, tableClassName) {

    var i, tabsContent, tabsLink;

    tabsContent = document.getElementsByClassName("tab-content");
    for (i = 0; i < tabsContent.length; i++) {
        tabsContent[i].style.display = "none";
    }

    tabsLink = document.getElementsByClassName("tab-link");
    for (i = 0; i < tabsLink.length; i++) {
        tabsLink[i].style.backgroundColor = "";
        tabsLink[i].style.color = "white";
    }

    document.getElementById(tableId).style.display = "block";
    tableClassName.style.backgroundColor = "#ddd";
    tableClassName.style.color = "black";
}

function smoothPanelBorder(panelLinksClassName, radiusValue) {
    var tabsLink = document.getElementsByClassName(panelLinksClassName);
    for (var i = 0; i < tabsLink.length; i++){
        tabsLink[0].style.borderTopLeftRadius = radiusValue;
        tabsLink[0].style.borderBottomLeftRadius = radiusValue;
        tabsLink[tabsLink.length - 1].style.borderTopRightRadius = radiusValue;
        tabsLink[tabsLink.length - 1].style.borderBottomRightRadius = radiusValue;
    }
}