public class LineCard extends Card{
    public enum LineTypes {SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
     THURSDAY, FRIDAY, SATURDAY 
    }
    
    public LineType lineType;
    
    @Override
    public int execute(int cyclesLeft, Player selfPlayer, Player otherPlayer) {
        switch (LineTypes) {
            case 1:  LineTypes = "MONDAY";
                     break;
            case 2:  monthString = "SUNDAY";
                     break;
            case 3:  monthString = "March";
                     break;
            case 4:  monthString = "April";
                     break;
            case 5:  monthString = "May";
                     break;
            case 6:  monthString = "June";
                     break;
            case 7:  monthString = "July";
                     break;
            case 8:  monthString = "August";
                     break;
            case 9:  monthString = "September";
                     break;
            case 10: monthString = "October";
                     break;
            case 11: monthString = "November";
                     break;
            case 12: monthString = "December";
                     break;
            default: monthString = "Invalid month";
                     break;
    }
    
}
