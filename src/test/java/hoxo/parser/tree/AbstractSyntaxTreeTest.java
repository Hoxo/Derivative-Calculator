package hoxo.parser.tree;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class AbstractSyntaxTreeTest {

    private AbstractSyntaxTree ast;
    private AbstractSyntaxTree.Iterator iterator;

    @Before
    public void init() {
        ast = new AbstractSyntaxTree();
        iterator = ast.iterator();
    }

    @Test
    public void astRootTest() {
        Optional<Leaf> root = iterator
                .setChild(Nodes.variable("123"));
        Assert.assertTrue(root.isPresent());
        Assert.assertTrue(iterator.current().isPresent());
        Assert.assertEquals(root, iterator.root());
    }

    @Test
    public void emptyAstTest() {
        Assert.assertFalse(iterator.hasParent());
        Assert.assertFalse(iterator.hasChildren());
        Assert.assertFalse(iterator.current().isPresent());
        Assert.assertFalse(iterator.hasCurrent());
    }

    @Test
    public void addParentTest() {
        Intermediary sum = Nodes.plus();
        Intermediary mult = Nodes.multiply();
        Leaf value = Nodes.variable("123");
        iterator.setChild(value);
        iterator.addParent(mult);
        iterator.lastChild();
        iterator.addParentWithPriority(sum);
        Assert.assertFalse(iterator.hasParent());
        Assert.assertEquals(Lists.newArrayList(mult), iterator.getChildren());
        iterator.lastChild();
        Assert.assertEquals(sum, iterator.getParent().get());
        Assert.assertEquals(Lists.newArrayList(value), iterator.getChildren());
    }

    @Test
    public void addParentWithPriorityTest() {
        Leaf leaf = Nodes.value("123");
        Intermediary sum1 = Nodes.plus();
        Intermediary sum2 = Nodes.plus();
        Intermediary mult = Nodes.multiply();
        Intermediary scope = Nodes.scope();
        iterator.setChild(leaf);
        iterator.addParentWithPriority(sum1);
        iterator.lastChild();
        iterator.addParentWithPriority(mult);
        iterator.lastChild();
        iterator.addParentWithPriority(scope);
        iterator.lastChild();
        iterator.addParentWithPriority(sum2);
        iterator.lastChild();

        Assert.assertEquals(leaf, iterator.current().get());

        iterator.parent();
        Assert.assertEquals(sum2, iterator.current().get());
        Assert.assertEquals(Lists.newArrayList(leaf), iterator.getChildren());

        iterator.parent();
        Assert.assertEquals(scope, iterator.current().get());
        Assert.assertEquals(Lists.newArrayList(sum2), iterator.getChildren());

        iterator.parent();
        Assert.assertEquals(mult, iterator.current().get());
        Assert.assertEquals(Lists.newArrayList(scope), iterator.getChildren());

        iterator.parent();
        Assert.assertEquals(sum1, iterator.current().get());
        Assert.assertEquals(Lists.newArrayList(mult), iterator.getChildren());

    }

}
