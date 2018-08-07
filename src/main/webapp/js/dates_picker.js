$(function () {
    var options = $.extend({},
        $.datepicker.regional["${locale}"],{
            dateFormat: "yy-mm-dd",
            minDate: 0
        });
    $("#check_in").datepicker(options);
    $("#check_out").datepicker(options);
    var dateFormat = "yy-mm-dd",
        from = $("#check_in")
            .datepicker({
                minDate: 0,
                numberOfMonths: 1
            })
            .on("change", function () {
                to.datepicker("option", "minDate", getDate(this));
            }),
        to = $("#check_out")
            .datepicker({
                defaultDate: "minDate",
                numberOfMonths: 1
            })
            .on("change", function () {
                from.datepicker("option", "maxDate", getDate(this));
            });

    function getDate(element) {
        var date;
        try {
            date = $.datepicker.parseDate(dateFormat, element.value);
        } catch (error) {
            date = null;
        }
        return date;
    }
});