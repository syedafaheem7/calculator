package edu.qc.seclass.replace;

	import java.io.File;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.nio.charset.Charset;
	import java.nio.charset.StandardCharsets;
	import java.nio.file.Files;
	import java.nio.file.Path;
	import java.nio.file.Paths;

	public class Replace {
		
		private boolean backup;
		private boolean first;
		private boolean last;
		private boolean insensitive;
		private boolean doneWithCommands;
		private boolean doneWithWords;
		private boolean errorBol;
		private boolean commandSeparator;
		private String[] wordFrom;
		private String[] wordTo;
		private int wordFromIndex;
		private int wordToIndex;
		private int lastDashesIndex;
		
		public Replace(int argslength) 
		{
			backup = false;
			first = false;
			last = false;
			insensitive = false;
			doneWithCommands = false;
			doneWithWords = false;
			errorBol = false;
			commandSeparator = false;
			wordFrom = new String[argslength];
			wordTo = new String[argslength];
			wordFromIndex = 0;
			wordToIndex = 0;
			lastDashesIndex = 0;
		}
		
		public void argsManager(String[] mainArgs) {
			for (int i=0 ; i<mainArgs.length ; i++) {
			
				if (mainArgs[i]=="--") {
					lastDashesIndex = i;
				}
			}
	        for (int i=0 ; i<mainArgs.length ; i++) {
	        		if((mainArgs[i]=="--")&&(i==lastDashesIndex)){
	        			doneWithWords = true;
	        			if(((wordFrom[0]==null)||(wordTo[0]==null))&&(errorBol!=true)) {
	        				errorBol = true;
	        				usage();
	        			}
	        			if((wordFromIndex!=wordToIndex)&&(errorBol!=true)) {
	        				errorBol = true;
	        				usage();
	        			}
	        		}
	        		else {
	        			try {	replaceApp(mainArgs[i]);
	        			} catch (IOException e) {
	    				fileNotFound(mainArgs[i]);
	        			}
	        		}
	        }
		}

		private void replaceApp(String arg) throws IOException {
			if (errorBol==false){
				if ((doneWithCommands!=true)) {
					switch (arg) {
					case "-b":
						backup = true;
						break;
					case "-f":
						first = true;
						break;
					case "-l":
						last = true;
						break;
					case "-i":
						insensitive = true;
						break;
					default:
						doneWithCommands = true;
						break;
					}	
				}
				if (doneWithWords==true) {
					if (arg.length()>2) {
						replaceStrings(arg);
					}
					else {
						errorBol = true;
						usage();
					}
				}
				if ((doneWithCommands==true)&&(doneWithWords!=true)){
					assignWord(arg);
				}
			}
		}
		
		private void assignWord(String arg) {
			if ((arg=="--")&&(wordFrom[0]==null)&&(commandSeparator!=true)) {
				//This case ignores "--" as input if "--"
				//is meant to separate commands from input words
				commandSeparator = true;
			}
			else {
				if (wordFrom[wordToIndex] == null) {
					if ((arg=="")&&(errorBol!=true)) {
						errorBol = true;
						usage();
					}
					else {
						wordFrom[wordFromIndex] = arg;
						wordFromIndex++;
					}
				}
				else if ((wordFrom[wordToIndex]!=null)&&(wordTo[wordToIndex]==null)) {
					wordTo[wordToIndex] = arg;
					wordToIndex++;
				}
			}
		}
		
		private void replaceStrings(String filePath) throws IOException {
			Path path = Paths.get(filePath);
			Charset charset = StandardCharsets.UTF_8;
			String content = new String(Files.readAllBytes(path), charset);
			File file = new File(filePath);
			FileWriter fileWriter = new FileWriter(file);
			
			if(Paths.get(filePath) != null) {
				if (backup==true) {
					if ((Files.exists(Paths.get(file.getPath() + ".bck"))==true)) {
						backupExist(file);
					}
					else {
						File backupFile = new File(file.getPath()+".bck");
						FileWriter backupFileWriter = new FileWriter(backupFile);
						backupFileWriter.write(content);
						backupFileWriter.close();
					}
				}
				if (first==true) {
					if (insensitive == true) {
						for (int i=0;i<wordFromIndex;i++) {
							content = content.replaceFirst("(?i)"+wordFrom[i], wordTo[i]);
						}
					}
					else {
						for (int i=0;i<wordFromIndex;i++) {
							content = content.replaceFirst(wordFrom[i], wordTo[i]);
						}
					}
				}
				if (last==true) {
					StringBuilder sbContent = new StringBuilder();
					sbContent.append(content);
					sbContent.reverse();
					
					StringBuilder[] sbWordFrom;
					sbWordFrom = new StringBuilder[wordFromIndex];
					for(int i=0;i<wordFromIndex;i++) {
						sbWordFrom[i]=new StringBuilder();
						sbWordFrom[i].append(wordFrom[i]);
						sbWordFrom[i].reverse();
					}
					
					StringBuilder[] sbWordTo;
					sbWordTo = new StringBuilder[wordToIndex];
					for(int i=0;i<wordToIndex;i++) {
						sbWordTo[i]=new StringBuilder();
						sbWordTo[i].append(wordTo[i]);
						sbWordTo[i].reverse();
					}
					
					String reverseContent = sbContent.toString();
					if (insensitive == true) {
						for (int i=0;i<wordFromIndex;i++) {
							reverseContent = reverseContent.replaceFirst("(?i)"+sbWordFrom[i].toString(), sbWordTo[i].toString());
						}
					}
					else {
						for (int i=0;i<wordFromIndex;i++) {
							reverseContent = reverseContent.replaceFirst(sbWordFrom[i].toString(), sbWordTo[i].toString());
						}
					}
					
					StringBuilder sbReverseContent = new StringBuilder();
					sbReverseContent.append(reverseContent);
					sbReverseContent.reverse();
					content = sbReverseContent.toString();
				}
				if ((first!=true)&&(last!=true)){
					if (insensitive == true) {
						for (int i=0;i<wordFromIndex;i++) {
							content = content.replaceAll("(?i)"+wordFrom[i], wordTo[i]);
						}
					}
					else {
						for (int i=0;i<wordFromIndex;i++) {
							content = content.replaceAll(wordFrom[i], wordTo[i]);
						}
					}
				}
				fileWriter.write(content);
				fileWriter.close();
			} 
		}
		
	    public void usage() {
	        System.err.println("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- " + "<filename> [<filename>]*" );
	    }
	    
	    public void fileNotFound(String filePath) {
	    	File file = new File(filePath);
	    	System.err.println("File " + file.getName() + " not found");
	    	file.delete();
	    }
	    
	    public void backupExist(File file) {
	    	System.err.println("Not performing replace for " + file.getName() + ": Backup file already exists");
	    }

}
