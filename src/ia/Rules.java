package ia;

import entity.Case;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by alex on 05/11/2016.
 */
public class Rules {

    private int label;
    private List<String> _true;
    private List<String> _false;
    private List<String> _trueDir;
    private List<String> _falseDir;
    private List<String> actions;
    private boolean dirAvailable;
    private boolean looked;


    public Rules() {
        dirAvailable = false;
        actions = new ArrayList<>();
        _false = new ArrayList<>();
        _true = new ArrayList<>();
        _falseDir = new ArrayList<>();
        _trueDir = new ArrayList<>();

    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public boolean isLooked() {
        return looked;
    }

    public void setLooked(boolean looked) {
        this.looked = looked;
    }

    public List<String> get_true() {
        return _true;
    }

    public void set_true(List<String> _true) {
        this._true = _true;
    }

    public List<String> get_false() {
        return _false;
    }

    public void set_false(List<String> _false) {
        this._false = _false;
    }

    public List<String> get_falseDir() {
        return _falseDir;
    }

    public void set_falseDir(List<String> _falseDir) {
        this._falseDir = _falseDir;
    }

    public List<String> get_trueDir() {
        return _trueDir;
    }

    public void set_trueDir(List<String> _trueDir) {
        this._trueDir = _trueDir;
    }

    public boolean isDirAvailable() {
        return dirAvailable;
    }

    public void setDirAvailable(boolean dirAvailable) {
        this.dirAvailable = dirAvailable;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public boolean isOK(int posX, int posY, String dir){
        //if trigger is present faire action donc send true

        //if diravailable true une dir doit etre dispo
        if(dirAvailable && dirAvailable != Agent.getInstance().dirAvailable()){
            return false;
        }
        if(!_true.isEmpty()) {
            if(!checkTrue(_true,posX,posY)){
                return false;
            }
        }
        if(!_false.isEmpty()) {
            if(!checkFalse(_false,posX,posY)){
                return false;
            }
        }
        //get dir before
        switch (dir){
            case "right":
                posY++;
                break;
            case "left":
                posY--;
                break;
            case "up":
                posX--;
                break;
            case "down":
                posX++;
                break;
        }


        if(!_trueDir.isEmpty()) {
            if(!checkTrue(_trueDir,posX,posY)){
                return false;
            }
        }
        if(!_falseDir.isEmpty()) {
            if(!checkFalse(_falseDir,posX,posY)){
                return false;
            }
        }
        return true;
    }

    private boolean checkTrue(List<String> list,int posX,int posY){
        for (String s : list
                ) {
            try {
                Method m = Agent.getInstance().getClass().getMethod("get"+s);
                Object b = m.invoke(Agent.getInstance());
                Boolean bol[][] = (Boolean[][]) b;
                if (!bol[posX][posY]) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private boolean checkFalse(List<String> list,int posX,int posY){
        for (String s : list
                ) {
            try {
                Method m = Agent.getInstance().getClass().getMethod("get"+s);
                Object b = m.invoke(Agent.getInstance());
                Boolean bol[][] = (Boolean[][]) b;
                if (bol[posX][posY]) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}

