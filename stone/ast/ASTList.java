package stone.ast;

import java.util.List;
import java.util.Iterator;

public class ASTList extends ASTree {
    protected List<ASTree> children;

    public ASTList(List<ASTree> list) {
        children = list;
    }

    public ASTree child(int i) {
        return children.get(i);
    }

    public int numChildren() {
        return children.size();
    }

    public Iterator<ASTree> children() {
        return children.iterator();
    }

    public String location() {
        for (ASTree t : children) {
            String s = t.location();
            if (s != null)
                return s;
        }
        return null;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("\ttype: " + getClass().getName() + ",\n");
        builder.append("\tchildren: [\n\t");
        for (ASTree t : children) {
            builder.append(t.toString().replaceAll("\n", "\n\t") + ",\n\t");
        }
        builder.append("\t\n]");
        return builder.append("\n}").toString();
    }
}