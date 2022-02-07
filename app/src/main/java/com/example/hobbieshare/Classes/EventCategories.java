package com.example.hobbieshare.Classes;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class EventCategories {

    private Spinner eventMainType;
    private Spinner eventInnerType;

    private String[] mainSpinnerEventList= {"ספורט", "מוזיקה","אוכל", "משחקים","אומנות", "אוספים", "למידה ופיתוח אישי","אחר"};
    private String[] innerSpinnerEventList = {};
    private String[] innerSpinnerEventListSports= {"כדורגל", "כדורסל","טניס", "פינג-פונג", "פוטבול","אופניים", "ריצה", "פריזבי","כושר"};
    private String[] innerSpinnerEventListMusic= {"נגינה כללי", "כתיבה","גיטרה", "פסנתר", "תופים","שירה"};
    private String[] innerSpinnerEventListGames= {"משחקי מחשב", "משחקי קופסה","משחקים בחוץ","שחמט" ,"מציאות מדומה"};
    private String[] innerSpinnerEventListArt= {"ציור", "צילום","יצירה", "פיסול"};
    private String[] innerSpinnerEventListCollections= {"קלפים", "בולים","קומיקס", "משחקים", "אומנות","תקליטים"};
    private String[] innerSpinnerEventListLearning= {"בישול", "אסטרונומיה","תכנות", "יצירה", "אימונים"};
    private String[] innerSpinnerEventListOther= {"קסמים", "אוריגמי","גרפיטי"};
    private String[] innerSpinnerEventListFood = {"בישול", "אפייה","מסעדות"};

    private Context context;

    private ArrayAdapter<CharSequence> mainSpinnerAdapter;
    private ArrayAdapter<CharSequence> innerSpinnerAdapter;

    public void EventCategories(String[] mainSpinnerEventList, String[] innerSpinnerEventList, Context context) {
        this.mainSpinnerEventList = mainSpinnerEventList;
        this.innerSpinnerEventList = innerSpinnerEventList;
        this.context = context;
    }


//    public void setInnerSpinnerEventList(String[] innerSpinnerEventList) {
//        String[] currList = {""};
//        String selectedMainType = getSelectedEventType();
//        switch (selectedMainType) {
//            case "ספורט":
//                currList = innerSpinnerEventListSports;
//                break;
//            case "מוזיקה":
//                currList = innerSpinnerEventListMusic;
//                break;
//            case "אוכל":
//                currList = innerSpinnerEventListFood;
//                break;
//            case "משחקים":
//                currList = innerSpinnerEventListGames;
//                break;
//            case "אומנות":
//                currList = innerSpinnerEventListArt;
//                break;
//            case "אוספים":
//                currList = innerSpinnerEventListCollections;
//                break;
//            case "למידה ופיתוח אישי":
//                currList = innerSpinnerEventListLearning;
//                break;
//            case "אחר":
//                currList = innerSpinnerEventListOther;
//                break;
//            default:
//                break;
//        }
//
//        innerSpinnerAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, currList);
//        innerSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        eventInnerType.setAdapter(innerSpinnerAdapter);
//    }
}
