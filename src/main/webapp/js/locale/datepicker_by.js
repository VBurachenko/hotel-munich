( function( factory ) {
    if ( typeof define === "function" && define.amd ) {

        // AMD. Register as an anonymous module.
        define( [ "../widgets/datepicker" ], factory );
    } else {

        // Browser globals
        factory( jQuery.datepicker );
    }
}( function( datepicker ) {

    datepicker.regional.by = {
        closeText: "Зачыніць",
        prevText: "&#x3C;Папярэдні",
        nextText: "Наступны&#x3E;",
        currentText: "Сення",
        monthNames: [ "Студзень","Люты","Сакавік","Красавік","Травень","Чэрвень",
            "Ліпень","Жнівень","Верасень","Кастрычнік","Лістапад","Снежань" ],
        monthNamesShort: [ "Стд","Лют","Сак","Крс","Тра","Чэр",
            "Ліп","Жні","Вер","Кас","Ліс","Сне" ],
        dayNames: [ "нядзеля","панядзелак","аўторак","серада","чацвер","пятніца","субота" ],
        dayNamesShort: [ "няд","пан","аўт","сер","чац","пят","суб" ],
        dayNamesMin: [ "Нд","Пн","Ат","Ср","Чц","Пт","Сб" ],
        weekHeader: "Тыд",
        firstDay: 1,
        changeMonth: true,
        changeYear: true,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: "" };
    datepicker.setDefaults( datepicker.regional.ru );

    return datepicker.regional.by;

} ) );