package com.urban.EmployeeManager.enums;

public enum Position {
    MANAGER("Trưởng phòng", "部長"),
    LEADER("Trưởng nhóm", "リーダー"),
    STAFF("Nhân viên", "一般社員"),
    SUPERVISOR("Giám sát", "課長");

    private final String vietnameseName;
    private final String japaneseName;

    Position(String vietnameseName, String japaneseName) {
        this.vietnameseName = vietnameseName;
        this.japaneseName = japaneseName;
    }

    public String getVietnameseName() {
        return vietnameseName;
    }

    public String getJapaneseName() {
        return japaneseName;
    }
}
