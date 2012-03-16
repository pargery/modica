package org.modica.web.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This service is used to manage the session scoped AfpModel object:
 * It will coordinate the AFP file management and the AFP document building
 *
 */
public class AfpService {

    private static final Logger LOG = LoggerFactory.getLogger(AfpService.class);

    private IAfpState afpState;

    private final ThreadLocal<FileInputStream> fileInputStore;

    private AfpTreeBuilder afpTreeBuilder;

    public AfpService() {
        this.fileInputStore = new ThreadLocal<FileInputStream>();
    }

    public void load(File afpFile) throws IOException {
        clear();
        openStream(afpFile);
    }

    public SfTreeNode getRootNode() throws IOException {
        SfTreeNode sfTreeNode = getSfTreeNode();
        if (sfTreeNode == null) {
            File file = getAfpFile();
            if (file != null) {
                FileInputStream input = new FileInputStream(file);
                sfTreeNode = buildTree(file);
                setSfTreeNode(sfTreeNode);
            }
        }
        return sfTreeNode;
    }

    private SfTreeNode buildTree(File file) throws IOException {
        return afpTreeBuilder.buildTree(file);
    }

    private void clear() throws IOException {
        File previous = getAfpFile();
        if (previous != null) {
            previous.delete();
        }
        setAfpFile(null);
        setSfTreeNode(null);
        FileInputStream input = getFileInput();
        setFileInput(null);
        if (input != null) {
            input.close();
        }
    }

    private void openStream(File afpFile) throws FileNotFoundException {
        setAfpFile(afpFile);
        fileInputStore.set(new FileInputStream(afpFile));
    }

    private File getAfpFile() {
        return afpState.getAfpFile();
    }

    private void setAfpFile(File afpFile) {
        afpState.setAfpFile(afpFile);
    }

    private SfTreeNode getSfTreeNode() {
        return afpState.getSfTreeNode();
    }

    private void setSfTreeNode(SfTreeNode sfTreeNode) {
        afpState.setSfTreeNode(sfTreeNode);
    }

    private FileInputStream getFileInput() {
        return fileInputStore.get();
    }

    private void setFileInput(FileInputStream input) {
        fileInputStore.set(input);
    }

    void beginRequest() throws IOException {
        File afpFile = getAfpFile();
        if (afpFile != null) {
            setFileInput(new FileInputStream(afpFile));
        }
        SfTreeNode sfTreeNode = getSfTreeNode();
        if (sfTreeNode != null) {
            afpTreeBuilder.attach(sfTreeNode, getFileInput());
        }
        LOG.debug("beginRequest()");
    }

    void endRequest() throws IOException {
        LOG.debug("ending request...");
        SfTreeNode sfTreeNode = getSfTreeNode();
        if (sfTreeNode != null) {
            afpTreeBuilder.detach(sfTreeNode);
        }
        FileInputStream input = fileInputStore.get();
        fileInputStore.set(null);
        if (input != null) {
            input.close();
        }
        LOG.debug("...request ended");
    }

    public void setAfpTreeBuilder(AfpTreeBuilder afpTreeBuilder) {
        this.afpTreeBuilder = afpTreeBuilder;
    }

    public void setAfpState(IAfpState afpState) {
        this.afpState = afpState;
    }
}
