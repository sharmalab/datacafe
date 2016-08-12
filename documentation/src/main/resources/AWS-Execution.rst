# Configuring Mongo in EC2.

Connecting to the remote EC2/Mongo Instance

 $ ssh -i "pradeeban.pem" ubuntu@ec2-54-166-90-207.compute-1.amazonaws.com

Copy files to the remote instance.

 $ scp -i "pradeeban.pem" /home/pradeeban/gsoc2015/conf/clinical.csv ubuntu@ec2-54-157-62-174.compute-1.amazonaws.com:/home/ubuntu

 $ scp -i "pradeeban.pem" /home/pradeeban/gsoc2015/conf/pathology.csv ubuntu@ec2-54-157-62-174.compute-1.amazonaws.com:/home/ubuntu



# Execute in Amazon EC2 Instances and Elastic MapReduce (Amazon EMR).

* SSH to remote instance

 $ ssh -i "pradeeban.pem" ubuntu@ec2-54-157-222-55.compute-1.amazonaws.com:/home/ubuntu/


* Copy All to the EC2 Instance

 $ scp -r -i "pradeeban.pem" . ubuntu@ec2-54-157-222-55.compute-1.amazonaws.com:/home/ubuntu/datacafe


* Copy only the datacafe jar

 $ scp -i "pradeeban.pem" lib/datacafe-server-1.0-SNAPSHOT.jar ubuntu@ec2-54-157-222-55.compute-1.amazonaws.com:/home/ubuntu/datacafe/lib



# Configure Drill in EMR

* Launch Drill during EMR instance bootstrap action

 s3://maprtech-emr/scripts/mapr_drill_bootstrap.sh
