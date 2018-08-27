function calcNightCount(fromDate, toDate) {

    var from = new Date(fromDate).getTime();
    var to = new Date(toDate).getTime();

    var milliSecInDay = 1000 * 60 * 60 * 24;

    var diffInMillSeconds = Math.abs(to - from);

    document.getElementById("nightCount").innerText = Math.ceil(diffInMillSeconds / milliSecInDay).toString();

}