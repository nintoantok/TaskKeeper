package gyri.taskkeeper.entities;

/**
 * Created by CWL on 27-12-2014.
 */
public class TaskEntity {

    Long _Id;
    String _TaskTitle;
    String _TaskDescription;
    String _StartDate;
    String _StartTime;
    String _StopDate;
    String _StopTime;

    public Long get_Id() {
        return _Id;
    }

    public void set_Id(Long _Id) {
        this._Id = _Id;
    }

    public String get_TaskTitle() {
        return _TaskTitle;
    }

    public void set_TaskTitle(String _TaskTitle) {
        this._TaskTitle = _TaskTitle;
    }

    public String get_TaskDescription() {
        return _TaskDescription;
    }

    public void set_TaskDescription(String _TaskDescription) {
        this._TaskDescription = _TaskDescription;
    }

    public String get_StartDate() {
        return _StartDate;
    }

    public void set_StartDate(String _StartDate) {
        this._StartDate = _StartDate;
    }

    public String get_StartTime() {
        return _StartTime;
    }

    public void set_StartTime(String _StartTime) {
        this._StartTime = _StartTime;
    }

    public String get_StopDate() {
        return _StopDate;
    }

    public void set_StopDate(String _StopDate) {
        this._StopDate = _StopDate;
    }

    public String get_StopTime() {
        return _StopTime;
    }

    public void set_StopTime(String _StopTime) {
        this._StopTime = _StopTime;
    }
}
