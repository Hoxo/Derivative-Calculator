package hoxo.parser;

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
        Optional<AbstractSyntaxTree.Leaf> root = iterator
                .addChild(AbstractSyntaxTree.Leaf.variable("123"));
        Assert.assertTrue(root.isPresent());
        Assert.assertTrue(iterator.current().isPresent());
        Assert.assertEquals(root, iterator.root());
    }

    @Test
    public void emptyAstTest() {
        Assert.assertFalse(iterator.hasParent());
        Assert.assertFalse(iterator.hasChildren());
        Assert.assertFalse(iterator.current().isPresent());
    }

    @Test
    public void addSiblingTest() {
        AbstractSyntaxTree.Intermediary i = AbstractSyntaxTree.Intermediary.sum();
        iterator.addChild(i);
        AbstractSyntaxTree.Leaf l1 = AbstractSyntaxTree.Leaf.variable("1");
        AbstractSyntaxTree.Leaf l2 = AbstractSyntaxTree.Leaf.variable("2");
        iterator.addChild(l1);
        iterator.child(0);
        iterator.addSibling(l2);
        Assert.assertEquals(i, l1.getParent());
        Assert.assertEquals(i, l2.getParent());

        Assert.assertTrue(iterator.current().isPresent());
        Assert.assertEquals(l1, iterator.current().get());
        Assert.assertTrue(iterator.hasParent());
        Assert.assertEquals(i, iterator.parent().get());
        Assert.assertEquals(Lists.newArrayList(l1, l2), iterator.current().get().asIntermediary().getChildren());
    }

    @Test
    public void addParentTest() {
        AbstractSyntaxTree.Intermediary sum = AbstractSyntaxTree.Intermediary.sum();
        AbstractSyntaxTree.Intermediary mult = AbstractSyntaxTree.Intermediary.multiply();
        AbstractSyntaxTree.Leaf value = AbstractSyntaxTree.Leaf.variable("123");
        iterator.addChild(value);
        iterator.addParent(mult);
        iterator.addParentWithPriority(sum);
        Assert.assertTrue(iterator.hasParent());
        iterator.parent();
        Assert.assertEquals(Lists.newArrayList(value), iterator.getChildren());
        iterator.parent();
        Assert.assertEquals(Lists.newArrayList(mult), iterator.getChildren());
    }

    @Test
    public void addParentWithPriorityTest() {
        AbstractSyntaxTree.Leaf leaf = AbstractSyntaxTree.Leaf.value("123");
        AbstractSyntaxTree.Intermediary sum1 = AbstractSyntaxTree.Intermediary.sum();
        AbstractSyntaxTree.Intermediary sum2 = AbstractSyntaxTree.Intermediary.sum();
        AbstractSyntaxTree.Intermediary mult = AbstractSyntaxTree.Intermediary.multiply();
        AbstractSyntaxTree.Intermediary scope = AbstractSyntaxTree.Intermediary.scope();
        iterator.addChild(leaf);
        iterator.addParentWithPriority(sum1);
        iterator.addParentWithPriority(mult);
        iterator.addParentWithPriority(scope);
        iterator.addParentWithPriority(sum2);
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
