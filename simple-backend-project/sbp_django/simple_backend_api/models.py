from django.db import models

# Create your models here.

class Publisher(models.Model):
    name = models.CharField(max_length=100)

    def __str__(self):
        return self.name
    
class Game(models.Model):
    name = models.CharField(max_length=100)
    pub_date = models.DateField()
    publisher = models.ForeignKey(Publisher, on_delete=models.DO_NOTHING)
    inventory_count = models.BigIntegerField(default=0)

    def __str__(self):
        return "%s (%s)" % (self.name, self.pub_date)

