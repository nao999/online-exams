function changeLevel(level){
    let str = "";
    switch (level){
        case 1:
            str = "初 一";
            break;
        case 2:
            str = "初 二";
            break;
        case 3:
            str = "初 三";
            break;
        case 4:
            str = "高 一";
            break;
        case 5:
            str = "高 二";
            break;
        case 6:
            str = "高 三";
            break;

    }
    return str;
}

function changeClass(stuClass){
    let str = "";
    switch (stuClass){
        case 1:
            str = "1 班";
            break;
        case 2:
            str = "2 班";
            break;
        case 3:
            str = "3 班";
            break;
        case 4:
            str = "4 班";
            break;
        case 5:
            str = "5 班";
            break;
        case 6:
            str = "6 班";
            break;

    }
    return str;
}

export default{
    changeLevel,changeClass
}
