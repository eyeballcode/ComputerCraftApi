#!/usr/bin/env bash

clear

echo "****************************************************************"
echo "* Welcome to Eyeballcode's MCForge Dev Workspace Setup         *"
echo "* This should work with any MCForge version.                   *"
echo "* If you encounter any issues, please post a bug at my github  *"
echo "****************************************************************"

FORGESITE="http://files.minecraftforge.net/maven/net/minecraftforge/forge/"

declare -A forgeRec
declare -A forgeLatest

forgeRec=( ["1.5"]="1.5-7.7.0.598" ["1.5.1"]="1.5.1-7.7.2.682" ["1.5.2"]="1.5.2-7.8.1.737" ["1.6.1"]="1.6.1-8.9.0.775" ["1.6.2"]="1.6.2-9.10.1.871" ["1.6.3"]="1.6.3-9.11.0.878" ["1.6.4"]="1.6.4-9.11.1.1345" ["1.7.2"]="1.7.2-10.12.2.1121" ["1.7.10"]="1.7.10-10.13.2.1291" ["1.7.10_pre4"]="10.12.2.1149-prerelease" ["1.8"]="1.8-11.14.1.1334")

forgeLatest=( ["1.5"]="1.5-7.7.0.598" ["1.5.1"]="1.5.1-7.7.2.682" ["1.5.2"]="1.5.2-7.8.1.738" ["1.6.1"]="1.6.18.9.0.775" ["1.6.2"]="1.6.2-9.10.1.871" ["1.6.3"]="1.6.3-9.11.0.878" ["1.6.4"]="1.6.4-9.11.1.1345" ["1.7.2"]="1.7.2-10.12.2.1147" ["1.7.10"]="1.7.10-10.13.3.1408-1710" ["1.7.10_pre4"]="10.12.2.1149-prerelease" ["1.8"]="1.8-11.14.1.1405")

WORKINGDIR=$(pwd)

BUILDFILE="$WORKINGDIR/build.gradlew"

MCFVER=""

DOWNLOADURL=""

echo "You are in directory $WORKINGDIR"

printSpinner() {
  echo -n "|"
  sleep 1
  echo -n "/"
  sleep 1
  echo -n "-"
  sleep 1
  echo -n "\\"
  sleep 1
  echo -n "|"
  sleep 1
}

printStars() {
  echo -n "."
  sleep 1
  echo -n "."
  sleep 1
  echo -n "."
  sleep 1
  echo -n ""
}


Help() {
  
  echo -n "Loading Help..."
  echo ""
  echo -n "Downloading"
  printStars
  echo
  less help.txt
}

GHControlPanel() {
  
  done=""
  echo "Loading Github Control Panel."
  if [ ! -e $WORKINGDIR/gitRepo ]; then
    echo "Downloading latest GHRepoSetup from Github..."
    wget https://raw.githubusercontent.com/eyeballcode/GithubSetup/master/gitRepo
    chmod -f 700 gitRepo
    echo "Saved as gitRepo!"
  fi
  clear
  echo "No other files to download!"
  echo "Now, review the options: "
  echo "1) Link this folder to a GH Repo"
  echo "2) Create Github Repository(Will download quite a few files, including java if not installed.)"
  echo "3) Delete Github Repository(Will download quite a few files, including java if not installed.)"
  echo "4) Clone a repository"
  echo "9) Clear **ALL** Github content from this directory"
  echo "0) Exit"
  while [ "$done" == "" ]
  do
    read ANS
    case $ANS in
    1) echo "Loading GHRepoLink..."
    ./gitRepo
    done="Done"
    clear
    return;;
 
    2) echo "WIP"
    done="Done"
    return;;

    3) echo "WIP"
    done="Done"
    return;;
  
    4) echo "Git Clone"
    echo "Go to the repository site and look at the right. Find a textbox that says HTTPS clone URL. Copy it and paste it"
    echo -n "Link: "
    read LINK
    git clone $LINK
    echo "303 SEE OTHER....."
    ./gitRepo
    done="Done"
    return;;

    9) echo "Sure you want to clear? Enter \"Delete\" to confirm"
    read DELETE
    if [ "$DELETE" == "Delete" ]; then
      echo "Last warning! You have 3 seconds. Press ^C to cancel."
      sleep 3
      echo "Too late..."
      rm -rf .git/
      clear
    else
      clear
      echo "Not deleting."
    fi
    done="Done"
    return;;
  
    0) echo "Exit"
    clear
    return;;

    *) echo "Invalid Choice!"
    esac
  done
  sleep 3
  clear
}

CleanGradleFolder() {

  
  if [ -e $HOME/.gradle ]; then
    echo "Found .gradlew folder, deleting."
    echo -n "Are you sure? This will mess up all your old projects! Enter \"Delete\" to confirm. "
    read ANS
    echo "$ANS"
    if [ "$ANS" == "Delete" ]; then
      echo "Last warning. You have 2 seconds to press ^C to stop it."
      sleep 2
      rm -rf $HOME/.gradlew
      echo "Not deleting."
    else
      echo "Not deleting."
    fi
  else
    echo "Did not find .gradlew folder..."
  fi 
  sleep 3
  clear
}

ClearCache() {

  
  ls build.gradle
  ls gradlew
  if [ -e $WORKINGDIR/build.gradle ]; then
    if [ -e $WORKINGDIR/gradlew ]; then
      echo "Found build.gradlew and gradlew file. Assuming they are good and not fakes."
      ./gradlew cleanCache
    fi
  else
    echo "Did not find build.gradlew or gradlew file. Check that you are in the right directory and they exist."
    echo "For your convinence, we will now print out all the files in your current folder."
    ls
  fi
  sleep 3
  clear
}

InstallJDK() {

  
  echo "Install SDK..."
  echo "SUDO: This may require your password."
  echo "If you don't want to enter your password, type this manually."
  echo "sudo apt-get install openjdk-7-jre openjdk-7-jre-lib openjdk-7-jdk openjdk-7-doc"
  echo "Click ^C now to cancel."
  sleep 3
  echo "Now installing..."
  sudo apt-get install openjdk-7-jre openjdk-7-jre-lib openjdk-7-jdk openjdk-7-doc
  sleep 3
  clear
}

InstallJava() {

  
  ynsdk=""
  WHICHJOUT=$(which java)
  if [ "$WHICHJOUT" == "" ]; then
    while [ "$ynsdk" == "" ]
    do
      echo "Did not find java, will you like to install a sdk? (Y/N)"
      read ANSWER
      if [ "$ANSWER" == "Y" ]; then
        ynsdk="Done!"
        InstallJDK
        return 1
      elif [ "$ANSWER" == "N" ]; then
        ynsdk="Done!"
        echo "Never mind then..."
        return 2
      else
        echo "Invalid choice!"
        return 3
      fi
    done
  else
    echo -n "We dected a java version: "
    echo -n "$(java -version)"
  fi
  return
  sleep 3
  clear
}

getMCFVersion() {

  

  doneMCF=""

  echo >&2 "1) MC 1.5"
  echo >&2 "2) MC 1.5.1"
  echo >&2 "3) MC 1.5.2"
  echo >&2 "-----------"
  echo >&2 "4) MC 1.6.1"
  echo >&2 "5) MC 1.6.2"
  echo >&2 "6) MC 1.6.3"
  echo >&2 "7) MC 1.6.4"
  echo >&2 "-----------"
  echo >&2 "8) MC 1.7.2"
  echo >&2 "9) MC 1.7.10"
  echo >&2 "10) MC 1.7.10_pre4"
  echo >&2 "-----------"
  echo >&2 "11) MC 1.8"

  echo >&2 "MCForge version: "

  while [ "$doneMCF" == "" ]
  do

      read VER

      case $VER in

      1 )
        MCFVER="1.5"
        doneMCF="Done!"
        return;;

      2 )
        MCFVER="1.5.1"
        doneMCF="Done!"
        return;;

      3 ) 
        MCFVER="1.5.2"
        doneMCF="Done!"
        return;;

      4 ) 
        MCFVER="1.6.1"
        doneMCF="Done!"
        return;;

      5 ) 
        MCFVER="1.6.2"
        doneMCF="Done!"
        return;;

      6 ) 
        MCFVER="1.6.3"
        doneMCF="Done!"
        return;;

      7 ) 
        MCFVER="1.6.4"
        doneMCF="Done!"
        return;;

      8 ) 
        MCFVER="1.7.2"
        doneMCF="Done!"
        return;;

      9 ) 
        MCFVER="1.7.10"
        doneMCF="Done!"
        return;;

      10 ) 
        MCFVER="1.7.10_pre4"
        doneMCF="Done!"
        return;;

      11 ) 
        MCFVER="1.8"
        doneMCF="Done!"
        return;;

      *)echo "Invalid choice"
        doneMCF=""
    esac
  done
  sleep 3
  clear
}

PostInstallGH() {
  
  echo "ERROR---TRANSMISSION INTERRUPTED. CODE 303 SEE OTHER."
  echo -n "Welcome fellow user, to the Github Revolution. Would you like our services?(Y/N) "
  read ANS
  if [ "$ANS" == "Y" ]; then
    echo "Ok then, loading script..."
    echo "Loading..."
    echo "Script loaded!"
    echo "Now running script!"
    GHControlPanel
  else
    echo "No? OK then."
    echo "You can always come back by entering 5 at the main menu. See you soon!"
  fi
}


InstallForge() {

  unzip forgeSrc.zip
  sleep 1
  ls -A
  rm CREDITS-fml.txt
  rm gradlew.bat
  rm LICENSE-fml.txt
  chmod 700 gradlew
  sleep 3
  clear
  ./gradlew setUpDecompWorkspace
  sleep 3
  clear
  ./gradlew eclipse --refresh-dependencies
  sleep 3
  clear

}

Install() {
  
  done=""
  clear
  echo -n "Installing MCForge Dev Workspace "
  #printStars
  echo
  while [ "$done" == "" ]
  do
    echo "Please select a MCForge Version to download: "
    getMCFVersion
    echo -n "Select Latest(1) or Recommended(2): "
    read type
    echo -n "Enter Project name: "
    read PRJNAME
    if [ -e $WORKINGDIR/$PRJNAME ]; then
      echo -n "WARNING: Found existing project $PRJNAME. would you like to override it? "
      read OVERRIDE
      if [ "$OVERRIDE" == "Y" ]; then
        echo "Overriding... You have 1 second to cancel."
        sleep 1
        rm -rf $OVERRIDE
      else
        echo "Not overriding."
      fi
    else
      mkdir $PRJNAME
    fi
    cd $PRJNAME
    echo -n "Delete existing files: (Y/N) "
    read shouldDelete
    if [ -e $WORKINGDIR/forge-${forgeLatest["$MCFVER"]}-src.zip ]; then
      echo "Found old forge download for this version, deleting."
      rm -rf forge-${forgeLatest["$MCFVER"]}-src.zip
    fi
    if [ "$shouldDelete" == "Y" ]; then
      ls -A | grep -v "$(basename $0)" | grep -v "help.txt" | xargs rm -rf
    fi
    case $type in
      1)
        DOWNLOADURL="$FORGESITE${forgeLatest["$MCFVER"]}/forge-${forgeLatest["$MCFVER"]}-src.zip"
        wget $DOWNLOADURL
        mv "forge-${forgeLatest["$MCFVER"]}-src.zip" forgeSrc.zip
        InstallForge
        done="Done!"
        PostInstallGH
        return;;
      2)
        DOWNLOADURL="$FORGESITE${forgeRec["$MCFVER"]}/forge-${forgeRec["$MCFVER"]}-src.zip"
        wget $DOWNLOADURL
        mv "forge-${forgeRec["$MCFVER"]}-src.zip" forgeSrc.zip
        unzip forgeSrc.zip
        InstallForge
        done="Done!"
        PostInstallGH
        return;;
      *)echo "Invalid choice"
        return

    esac
  done
  
  cd ..

  sleep 3
  clear
}

##################
#                #
# Main Menu      #
#                #
##################
mainMenu() {
    echo "What would you like to do?"
    echo "1) Install MCForge Dev Workspace"
    echo "2) Delete **ALL** MCForge things in your $HOME/.gradle folder"
    echo "3) Clean all Gradle chache from this directory"
    echo "4) Check and install Java (If needed)"
    echo "5) Github VCS control panel"
    echo "9) Help"
    echo "0) Exit"
    read CHOICE
    case $CHOICE in
      1 ) clear
      Install
      return;;
      2 ) clear
      CleanGradleFolder
      return;;
      3 ) clear
       ClearCache 
      return;;
      4 ) clear
      InstallJava
      return;;
      5 ) clear
      GHControlPanel
      return;;
      9 ) Help
      clear
      return;;
      0) exit
      return;;
      * ) echo "Invalid Choice!"
      return
    esac
}
while true
do
  
  mainMenu
done
